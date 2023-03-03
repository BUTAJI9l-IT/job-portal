package muni.fi.cz.jobportal.api.common;

import java.util.UUID;
import lombok.Data;
import muni.fi.cz.jobportal.enums.CompanyNumberOfEmployees;

@Data
public class CompanyDto {

  private UUID id;
  private UUID userId;
  private String companyName;
  private CompanyNumberOfEmployees companySize;
}
