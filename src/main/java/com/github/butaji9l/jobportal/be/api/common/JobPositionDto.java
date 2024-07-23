package com.github.butaji9l.jobportal.be.api.common;

import com.github.butaji9l.jobportal.be.enums.PositionState;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class JobPositionDto {

  private UUID id;
  private String positionName;
  private PositionState status;
  private ReferenceUserDto company;
  private Boolean applied;
  private Boolean favourite;
  private List<ReferenceDto> jobCategories;

}
