package muni.fi.cz.jobportal.service;

import muni.fi.cz.jobportal.api.common.LoginResponse;
import muni.fi.cz.jobportal.api.common.RegistrationRequest;
import muni.fi.cz.jobportal.api.request.LoginRequest;
import muni.fi.cz.jobportal.api.request.RefreshTokenRequest;
import org.springframework.lang.NonNull;

public interface AuthenticationService {

  @NonNull
  LoginResponse performLogin(@NonNull LoginRequest request);

  @NonNull
  LoginResponse refresh(@NonNull RefreshTokenRequest request);

  @NonNull
  LoginResponse registerUser(@NonNull RegistrationRequest request);
}
