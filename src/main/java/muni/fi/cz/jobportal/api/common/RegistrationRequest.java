package muni.fi.cz.jobportal.api.common;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import muni.fi.cz.jobportal.enums.JobPortalScope;

@Data
public class RegistrationRequest {

  @NotNull
  private JobPortalScope scope;
  @NotNull
  @Email
  private String email;
  @NotNull
  @Size(min = 8)
  private String password;
  @NotNull
  @Size(min = 8)
  private String repeatPassword;
}
