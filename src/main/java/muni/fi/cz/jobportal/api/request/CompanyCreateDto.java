package muni.fi.cz.jobportal.api.request;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import muni.fi.cz.jobportal.enums.CompanyNumberOfEmployees;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyCreateDto {

  private UUID user;
  private String companyName;
  private String companyLink;
  private CompanyNumberOfEmployees companySize;
  private String description;
}
