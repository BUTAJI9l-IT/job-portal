package muni.fi.cz.jobportal.api.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RefreshTokenRequest {

  @NotBlank
  private String refreshToken;
}
