package com.github.butaji9l.jobportal.be.service.impl;

import com.github.butaji9l.jobportal.be.annotation.JobPortalService;
import com.github.butaji9l.jobportal.be.api.JwtClaims;
import com.github.butaji9l.jobportal.be.api.common.LoginResponse;
import com.github.butaji9l.jobportal.be.api.common.RegistrationRequest;
import com.github.butaji9l.jobportal.be.api.request.LoginRequest;
import com.github.butaji9l.jobportal.be.api.request.RefreshTokenRequest;
import com.github.butaji9l.jobportal.be.configuration.properties.JobPortalApplicationProperties;
import com.github.butaji9l.jobportal.be.domain.RefreshToken;
import com.github.butaji9l.jobportal.be.domain.User;
import com.github.butaji9l.jobportal.be.enums.JobPortalScope;
import com.github.butaji9l.jobportal.be.exception.EmptyScopesException;
import com.github.butaji9l.jobportal.be.exception.EntityNotFoundException;
import com.github.butaji9l.jobportal.be.mapper.UserMapper;
import com.github.butaji9l.jobportal.be.repository.RefreshTokenRepository;
import com.github.butaji9l.jobportal.be.repository.UserRepository;
import com.github.butaji9l.jobportal.be.service.AuthenticationService;
import com.github.butaji9l.jobportal.be.service.UserService;
import com.github.butaji9l.jobportal.be.utils.StaticObjectFactory;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

/**
 * {@link AuthenticationService} Implementation
 *
 * @author Vitalii Bortsov
 */
@JobPortalService
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final JobPortalApplicationProperties applicationProperties;
  private final UserMapper userMapper;
  private final StaticObjectFactory staticObjectFactory;
  private final UserRepository userRepository;
  private final UserService userService;
  private final RefreshTokenRepository refreshTokenRepository;
  private final JwtEncoder jwtEncoder;
  private final JwtDecoder jwtDecoder;

  @NonNull
  @Override
  public LoginResponse performLogin(@NonNull LoginRequest request) {
    final var authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        request.getEmail(),
        request.getPassword()
      ));

    if (!authentication.isAuthenticated()) {
      throw new AccessDeniedException("Unauthorized");
    }

    final var user = userRepository.findByEmail(request.getEmail())
      .orElseThrow(() -> new EntityNotFoundException(User.class));
    return performLoginForUser(authentication, user.getId());
  }

  @NonNull
  @Override
  public LoginResponse refresh(@NonNull RefreshTokenRequest request) {
    final var claims = jwtDecoder.decode(request.getRefreshToken());

    if (!refreshTokenRepository.exists(request.getRefreshToken())) {
      throw new AuthorizationServiceException("Refresh token not found.");
    }

    final var user = userRepository.findById(UUID.fromString(claims.getSubject())).orElseThrow();
    final var now = Instant.now();

    final var accessTokenClaims = JwtClaimsSet.builder().claims(c -> c.putAll(claims.getClaims()))
      .expiresAt(now.plus(applicationProperties.getAccessToken().getDuration()))
      .id(staticObjectFactory.getRandomId().toString()).build();

    final var refreshExpiry = now.plus(applicationProperties.getRefreshToken().getDuration());
    final var refreshTokenClaims = JwtClaimsSet.builder().claims(c -> c.putAll(claims.getClaims()))
      .expiresAt(refreshExpiry).id(staticObjectFactory.getRandomId().toString()).build();

    return getLoginResponse(user, accessTokenClaims, refreshTokenClaims);
  }

  @Scheduled(cron = "@hourly")
  public void clearExpiredRefreshTokens() {
    refreshTokenRepository.deleteExpired();
  }

  @NonNull
  @Override
  public LoginResponse registerUser(@NonNull RegistrationRequest request) {
    if (request.getScope().equals(JobPortalScope.ADMIN)) {
      throw new AccessDeniedException("Cannot be registered as an administrator");
    }
    final var userId = userService.create(userMapper.map(request)).getId();
    return performLoginForUser(authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(request.getEmail(),
        request.getPassword().getPassword())), userId);
  }

  private JwtClaimsSet createAccessToken(UUID id, User user, Instant now,
    Authentication authentication) {
    final var claims = JwtClaimsSet.builder()
      .id(id.toString())
      .issuedAt(now);

    final var authorities = authentication.getAuthorities();
    claims.subject(user.getId().toString());
    claims.claim(JwtClaims.EMAIL_CLAIM, user.getEmail());
    claims.claim(JwtClaims.LANGUAGE_CLAIM, user.getPreferences().getLanguage());
    claims.claim(JwtClaims.NON_USER_UUID_CLAIM, user.getNUI().toString());
    claims.claim(JwtClaims.SCOPE_CLAIM, authorities.stream()
      .map(GrantedAuthority::getAuthority)
      .findFirst()
      .orElseThrow(EmptyScopesException::new));
    claims.subject(user.getId().toString());
    claims.expiresAt(now.plus(applicationProperties.getAccessToken().getDuration()));
    return claims.build();
  }

  private LoginResponse performLoginForUser(Authentication authentication, UUID userId) {
    final var user = userRepository.getOneByIdOrThrowNotFound(userId);
    final var now = staticObjectFactory.getNowAsInstant();

    final var accessTokenClaims = createAccessToken(staticObjectFactory.getRandomId(), user, now,
      authentication);
    final var refreshTokenClaims = JwtClaimsSet.from(accessTokenClaims)
      .expiresAt(now.plus(applicationProperties.getRefreshToken().getDuration())).build();

    return getLoginResponse(user, accessTokenClaims, refreshTokenClaims);
  }

  private LoginResponse getLoginResponse(User user, JwtClaimsSet accessTokenClaims,
    JwtClaimsSet refreshTokenClaims) {
    final var accessTokenValue = jwtEncoder.encode(JwtEncoderParameters.from(accessTokenClaims))
      .getTokenValue();
    final var refreshTokenValue = jwtEncoder.encode(JwtEncoderParameters.from(refreshTokenClaims))
      .getTokenValue();
    refreshTokenRepository.save(
      new RefreshToken(refreshTokenValue, refreshTokenClaims.getExpiresAt(), user));
    return new LoginResponse(accessTokenValue, refreshTokenValue);
  }
}
