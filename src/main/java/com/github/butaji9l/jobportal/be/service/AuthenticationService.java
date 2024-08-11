package com.github.butaji9l.jobportal.be.service;

import com.github.butaji9l.jobportal.be.api.common.LoginResponse;
import com.github.butaji9l.jobportal.be.api.common.RegistrationRequest;
import com.github.butaji9l.jobportal.be.api.request.LoginRequest;
import com.github.butaji9l.jobportal.be.api.request.RefreshTokenRequest;
import org.springframework.lang.NonNull;

public interface AuthenticationService {

  /**
   * Authorizes a user.
   *
   * @param request credentials for authorization
   * @return response with access and refresh tokens
   */
  @NonNull
  LoginResponse performLogin(@NonNull LoginRequest request);

  /**
   * Exchanges a refresh token for a new token pair.
   *
   * @param request valid refresh token
   * @return a new token pair
   */
  @NonNull
  LoginResponse refresh(@NonNull RefreshTokenRequest request);

  /**
   * Registers a new user in the system and returns a token pair.
   *
   * @param request registration payload
   * @return a token pair
   */
  @NonNull
  LoginResponse registerUser(@NonNull RegistrationRequest request);
}
