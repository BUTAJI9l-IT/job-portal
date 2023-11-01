package muni.fi.cz.jobportal.api.common;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RepeatPasswordDto {

  @NotNull
  @Size(min = 8)
  private String password;
  @NotNull
  @Size(min = 8)
  private String repeatPassword;
}
