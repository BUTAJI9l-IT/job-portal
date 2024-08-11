package com.github.butaji9l.jobportal.be.resource;

import com.github.butaji9l.jobportal.be.api.ApiTags;
import com.github.butaji9l.jobportal.be.api.common.LoginResponse;
import com.github.butaji9l.jobportal.be.api.common.RegistrationRequest;
import com.github.butaji9l.jobportal.be.api.request.LoginRequest;
import com.github.butaji9l.jobportal.be.api.request.RefreshTokenRequest;
import com.github.butaji9l.jobportal.be.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@Tag(name = ApiTags.AUTHORIZATION)
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
