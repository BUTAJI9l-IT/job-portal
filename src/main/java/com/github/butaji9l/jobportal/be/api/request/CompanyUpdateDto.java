package com.github.butaji9l.jobportal.be.api.request;

import com.github.butaji9l.jobportal.be.enums.CompanyNumberOfEmployees;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

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
