package muni.fi.cz.jobportal.api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtClaims {

  public static final String SCOPE_CLAIM = "scope";
  public static final String EMAIL_CLAIM = "email";
  public static final String NON_USER_UUID_CLAIM = "nui";
}
