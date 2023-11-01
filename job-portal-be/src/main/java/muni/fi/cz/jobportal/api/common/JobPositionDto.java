package muni.fi.cz.jobportal.api.common;

import lombok.Data;
import muni.fi.cz.jobportal.enums.PositionState;

import java.util.List;
import java.util.UUID;

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
