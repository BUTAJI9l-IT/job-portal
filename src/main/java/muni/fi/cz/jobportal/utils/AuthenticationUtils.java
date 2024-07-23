package muni.fi.cz.jobportal.utils;

import java.util.Optional;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimAccessor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Utility class for handling authorization header with access token.
 *
 * @author Vitalii Bortsov
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthenticationUtils {

  public static UUID getCurrentUser() {
    return Optional.ofNullable(getAccessToken())
      .map(JwtClaimAccessor::getSubject)
      .map(UUID::fromString)
      .orElse(null);
  }

  public static String getJwtFromHeader() {
    final var requestAttributes = RequestContextHolder.getRequestAttributes();

    return requestAttributes != null ? Optional.ofNullable(
      ((ServletRequestAttributes) requestAttributes).getRequest()
        .getHeader("Authorization")).map(auth -> auth.substring(7)).orElse(null) : null;
  }

  public static Jwt getAccessToken() {
    final var authentication = getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof Jwt token) {
      return token;
    }
    return null;
  }

  public static Optional<String> getClaim(@NonNull String claim) {
    final var accessToken = getAccessToken();
    return accessToken == null ? Optional.empty()
      : Optional.ofNullable(accessToken.getClaim(claim));
  }

  public static Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

}
