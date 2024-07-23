package muni.fi.cz.jobportal.resource;

import static muni.fi.cz.jobportal.api.ApiTags.AUTHORIZATION;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

/**
 * Controller with authorization endpoints.
 *
 * @author Vitalii Bortsov
 */
@Tag(name = AUTHORIZATION)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationResource {

  private final AuthenticationService authenticationService;

  @PostMapping("/login")
  @Operation(summary = "${api.auth.login.summary}", description = "${api.auth.login.description}")
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
    return ResponseEntity.ok(authenticationService.performLogin(request));
  }

  @PostMapping("/register")
  @Operation(summary = "${api.auth.register.summary}", description = "${api.auth.register.description}")
  public ResponseEntity<LoginResponse> register(@Valid @RequestBody RegistrationRequest request) {
    return ResponseEntity.ok(authenticationService.registerUser(request));
  }

  @PostMapping("/refresh")
  @Operation(summary = "${api.auth.refresh.summary}", description = "${api.auth.refresh.description}")
  public ResponseEntity<LoginResponse> refreshToken(
    @Valid @RequestBody RefreshTokenRequest request) {
    return ResponseEntity.ok(authenticationService.refresh(request));
  }

}
