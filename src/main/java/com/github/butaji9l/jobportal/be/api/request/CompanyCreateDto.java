package com.github.butaji9l.jobportal.be.api.request;

import com.github.butaji9l.jobportal.be.enums.CompanyNumberOfEmployees;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyCreateDto {

  @NotNull
  private UUID user;
  @NotBlank
  private String companyName;
  private String companyLink;
  private CompanyNumberOfEmployees companySize;
  private String description;
}
