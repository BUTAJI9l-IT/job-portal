package com.github.butaji9l.jobportal.be.api.request;

import com.github.butaji9l.jobportal.be.annotation.RepeatPassword;
import com.github.butaji9l.jobportal.be.api.common.RepeatPasswordDto;
import com.github.butaji9l.jobportal.be.enums.CompanyNumberOfEmployees;
import com.github.butaji9l.jobportal.be.enums.JobPortalScope;
import com.neovisionaries.i18n.LanguageCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserCreateDto {

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
