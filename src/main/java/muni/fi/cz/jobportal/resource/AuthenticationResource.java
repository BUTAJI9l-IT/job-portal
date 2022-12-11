package muni.fi.cz.jobportal.resource;

import static muni.fi.cz.jobportal.api.ApiTags.AUTHORIZATION;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.api.common.LoginResponse;
import muni.fi.cz.jobportal.api.common.RegistrationRequest;
import muni.fi.cz.jobportal.api.request.LoginRequest;
import muni.fi.cz.jobportal.api.request.RefreshTokenRequest;
import muni.fi.cz.jobportal.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = AUTHORIZATION)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationResource {

  private final AuthenticationService authenticationService;

  @PostMapping("/login")
  @Operation(summary = "Authorize")
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
    return ResponseEntity.ok(authenticationService.performLogin(request));
  }

  @PostMapping("/register")
  @Operation(summary = "Registration")
  public ResponseEntity<LoginResponse> register(@Valid @RequestBody RegistrationRequest request) {
    return ResponseEntity.ok(authenticationService.registerUser(request));
  }

  @PostMapping("/refresh")
  @Operation(summary = "Refresh tokens")
  public LoginResponse refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
    return authenticationService.refresh(request);
  }

}
