package muni.fi.cz.jobportal.api.detail;

import lombok.Data;
import muni.fi.cz.jobportal.api.common.ReferenceDto;
import muni.fi.cz.jobportal.api.common.ReferenceUserDto;
import muni.fi.cz.jobportal.enums.PositionState;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

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
