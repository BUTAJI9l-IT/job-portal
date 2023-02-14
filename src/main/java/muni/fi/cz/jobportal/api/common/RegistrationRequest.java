package muni.fi.cz.jobportal.api.common;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Data;
import muni.fi.cz.jobportal.annotation.RepeatPassword;
import muni.fi.cz.jobportal.enums.JobPortalScope;

@Data
public class RegistrationRequest {

  @NotNull
  private JobPortalScope scope;
  @NotNull
  @Email
  private String email;
  private String name;
  private String lastName;
  @RepeatPassword
  private RepeatPasswordDto password;
}
