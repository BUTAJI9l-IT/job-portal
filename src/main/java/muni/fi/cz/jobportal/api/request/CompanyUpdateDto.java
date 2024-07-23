package muni.fi.cz.jobportal.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import muni.fi.cz.jobportal.enums.CompanyNumberOfEmployees;

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
