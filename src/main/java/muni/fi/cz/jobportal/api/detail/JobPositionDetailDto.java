package muni.fi.cz.jobportal.api.detail;

import java.util.List;
import java.util.UUID;
import lombok.Data;
import muni.fi.cz.jobportal.api.common.ReferenceDto;
import muni.fi.cz.jobportal.enums.PositionState;

@Data
public class JobPositionDetailDto {

  private UUID id;
  private String positionName;
  private PositionState status;
  private String country;
  private String state;
  private String city;

  private String contactEmail;
  private String detail;
  private ReferenceDto company;

  private Boolean applied;
  private Integer appliedCount;
  private List<String> jobCategories;
}
