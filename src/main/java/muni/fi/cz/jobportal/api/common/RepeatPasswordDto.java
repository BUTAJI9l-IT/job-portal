package muni.fi.cz.jobportal.api.common;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RepeatPasswordDto {

  @NotNull
  @Size(min = 8)
  private String password;
  @NotNull
  @Size(min = 8)
  private String repeatPassword;
}
