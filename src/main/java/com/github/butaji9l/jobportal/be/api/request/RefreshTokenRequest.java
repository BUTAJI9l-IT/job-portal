package com.github.butaji9l.jobportal.be.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequest {

  @NotBlank
  private String refreshToken;
}
