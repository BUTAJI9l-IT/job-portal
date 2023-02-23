package muni.fi.cz.jobportal.api.detail;

import java.util.List;
import java.util.UUID;
import lombok.Data;
import muni.fi.cz.jobportal.domain.JobPosition;
import muni.fi.cz.jobportal.enums.CompanyNumberOfEmployees;

@Data
public class CompanyDetailDto {

  private UUID id;
  private String email;
  private String companyName;
  private String companyLink;
  private CompanyNumberOfEmployees companySize;
  private List<JobPosition> jobPositions;
  private String description;

}
