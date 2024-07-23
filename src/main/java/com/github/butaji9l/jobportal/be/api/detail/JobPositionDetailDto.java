package com.github.butaji9l.jobportal.be.api.detail;

import com.github.butaji9l.jobportal.be.api.common.ReferenceDto;
import com.github.butaji9l.jobportal.be.api.common.ReferenceUserDto;
import com.github.butaji9l.jobportal.be.enums.PositionState;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class JobPositionDetailDto {

  private UUID id;
  private String positionName;
  private PositionState status;
  private ReferenceUserDto company;
  private Boolean applied;
  private Boolean favourite;
  private List<ReferenceDto> jobCategories;
  private String country;
  private String state;
  private String city;

  private String contactEmail;
  private String detail;

  private Integer appliedCount;

  private Instant created;
  private Instant lastUpdated;
}
