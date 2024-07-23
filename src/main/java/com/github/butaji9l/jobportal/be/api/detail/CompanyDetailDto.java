package com.github.butaji9l.jobportal.be.api.detail;

import com.github.butaji9l.jobportal.be.api.common.JobPositionDto;
import com.github.butaji9l.jobportal.be.enums.CompanyNumberOfEmployees;
import java.util.List;
import java.util.UUID;
import lombok.Data;

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
