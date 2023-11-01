package muni.fi.cz.jobportal.api.detail;

import lombok.Data;
import muni.fi.cz.jobportal.api.common.JobPositionDto;
import muni.fi.cz.jobportal.enums.CompanyNumberOfEmployees;

import java.util.List;
import java.util.UUID;

@Data
public class CompanyDetailDto {

  private UUID id;
  private UUID userId;
  private String email;
  private String companyName;
  private String companyLink;
  private CompanyNumberOfEmployees companySize;
  private List<JobPositionDto> jobPositions;
  private String description;

}
