package muni.fi.cz.jobportal.api.common;

import com.neovisionaries.i18n.LanguageCode;
import lombok.Data;
import muni.fi.cz.jobportal.annotation.RepeatPassword;
import muni.fi.cz.jobportal.enums.CompanyNumberOfEmployees;
import muni.fi.cz.jobportal.enums.JobPortalScope;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class RegistrationRequest {

  @NotNull
  private JobPortalScope scope;
  @NotNull
  @Email
  private String email;
  private String name;
  private String lastName;
  @Valid
  @RepeatPassword
  private RepeatPasswordDto password;

  private String companyName;
  private String companyLink;
  private CompanyNumberOfEmployees companySize;
  private LanguageCode language;
}
