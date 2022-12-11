package muni.fi.cz.jobportal.api.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequest {

  @NotBlank
  private String refreshToken;
}
