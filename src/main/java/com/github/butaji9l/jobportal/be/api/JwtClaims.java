package com.github.butaji9l.jobportal.be.api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Class with JWT claims.
 *
 * @author Vitalii Bortsov
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtClaims {

  public static final String SCOPE_CLAIM = "scope";
  public static final String EMAIL_CLAIM = "email";
  public static final String LANGUAGE_CLAIM = "lang";
  public static final String NON_USER_UUID_CLAIM = "nui";
}
