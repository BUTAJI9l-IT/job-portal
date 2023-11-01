package muni.fi.cz.jobportal.api.common;

import lombok.Data;
import muni.fi.cz.jobportal.enums.CompanyNumberOfEmployees;

import java.util.UUID;

@Data
public class CompanyDto {

  private UUID id;
  private UUID userId;
  private String companyName;
  private CompanyNumberOfEmployees companySize;
}
