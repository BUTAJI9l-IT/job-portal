package muni.fi.cz.jobportal.service.impl;

import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.annotation.JobPortalService;
import muni.fi.cz.jobportal.api.common.LoginResponse;
import muni.fi.cz.jobportal.api.common.RegistrationRequest;
import muni.fi.cz.jobportal.api.request.LoginRequest;
import muni.fi.cz.jobportal.api.request.RefreshTokenRequest;
import muni.fi.cz.jobportal.configuration.properties.JobPortalApplicationProperties;
import muni.fi.cz.jobportal.domain.RefreshToken;
import muni.fi.cz.jobportal.domain.User;
import muni.fi.cz.jobportal.enums.JobPortalScope;
import muni.fi.cz.jobportal.exception.EmptyScopesException;
import muni.fi.cz.jobportal.exception.EntityNotFoundException;
import muni.fi.cz.jobportal.mapper.UserMapper;
import muni.fi.cz.jobportal.repository.RefreshTokenRepository;
import muni.fi.cz.jobportal.repository.UserRepository;
import muni.fi.cz.jobportal.service.AuthenticationService;
import muni.fi.cz.jobportal.service.UserService;
import muni.fi.cz.jobportal.utils.StaticObjectFactory;
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

import java.time.Instant;
import java.util.UUID;

import static muni.fi.cz.jobportal.api.JwtClaims.*;

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
    final var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
      request.getEmail(),
      request.getPassword()
    ));

    if (!authentication.isAuthenticated()) {
      throw new AccessDeniedException("Unauthorized");
    }

    final var user = userRepository.findByEmail(request.getEmail())
      .orElseThrow(() -> {
        throw new EntityNotFoundException(User.class);
      });
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
    final var now = staticObjectFactory.getNowAsInstant();

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
      new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword().getPassword())), userId);
  }

  private JwtClaimsSet createAccessToken(UUID id, User user, Instant now, Authentication authentication) {
    final var claims = JwtClaimsSet.builder()
      .id(id.toString())
      .issuedAt(staticObjectFactory.now());

    final var authorities = authentication.getAuthorities();
    claims.subject(user.getId().toString());
    claims.claim(EMAIL_CLAIM, user.getEmail());
    claims.claim(LANGUAGE_CLAIM, user.getPreferences().getLanguage().getCode());
    claims.claim(NON_USER_UUID_CLAIM, user.getNUI().toString());
    claims.claim(SCOPE_CLAIM, authorities.stream()
      .map(GrantedAuthority::getAuthority)
      .findFirst()
      .orElseThrow(() -> {
        throw new EmptyScopesException();
      }));
    claims.subject(user.getId().toString());
    claims.expiresAt(now.plus(applicationProperties.getAccessToken().getDuration()));
    return claims.build();
  }

  private LoginResponse performLoginForUser(Authentication authentication, UUID userId) {
    final var user = userRepository.getOneByIdOrThrowNotFound(userId);
    final var now = staticObjectFactory.getNowAsInstant();

    final var accessTokenClaims = createAccessToken(staticObjectFactory.getRandomId(), user, now, authentication);
    final var refreshTokenClaims = JwtClaimsSet.from(accessTokenClaims)
      .expiresAt(now.plus(applicationProperties.getRefreshToken().getDuration())).build();

    return getLoginResponse(user, accessTokenClaims, refreshTokenClaims);
  }

  private LoginResponse getLoginResponse(User user, JwtClaimsSet accessTokenClaims, JwtClaimsSet refreshTokenClaims) {
    final var accessTokenValue = jwtEncoder.encode(JwtEncoderParameters.from(accessTokenClaims)).getTokenValue();
    final var refreshTokenValue = jwtEncoder.encode(JwtEncoderParameters.from(refreshTokenClaims)).getTokenValue();
    refreshTokenRepository.save(new RefreshToken(refreshTokenValue, refreshTokenClaims.getExpiresAt(), user));
    return new LoginResponse(accessTokenValue, refreshTokenValue);
  }
}
