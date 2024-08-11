package com.github.butaji9l.jobportal.be.api.common;

import com.github.butaji9l.jobportal.be.enums.CompanyNumberOfEmployees;
import java.util.UUID;
import lombok.Data;

@Data
public class CompanyDto {

  private UUID id;
  private UUID userId;
  private String companyName;
  private CompanyNumberOfEmployees companySize;
}
