package muni.fi.cz.jobportal.service;

import muni.fi.cz.jobportal.api.common.LoginResponse;
import muni.fi.cz.jobportal.api.common.RegistrationRequest;
import muni.fi.cz.jobportal.api.request.LoginRequest;
import muni.fi.cz.jobportal.api.request.RefreshTokenRequest;
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
