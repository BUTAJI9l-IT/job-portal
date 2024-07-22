package muni.fi.cz.jobportal.api.request;

import lombok.Data;
import muni.fi.cz.jobportal.enums.CompanyNumberOfEmployees;

import javax.validation.constraints.NotBlank;

@Data
public class CompanyUpdateDto {

  private String name;
  private String lastName;
  @NotBlank
  private String companyName;
  private String companyLink;
  private String description;

  private CompanyNumberOfEmployees companySize;
}
