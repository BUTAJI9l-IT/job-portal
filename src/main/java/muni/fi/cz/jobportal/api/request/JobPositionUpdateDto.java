package muni.fi.cz.jobportal.api.request;

import lombok.Data;
import muni.fi.cz.jobportal.enums.PositionState;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
public class JobPositionUpdateDto {

  @NotNull
  private PositionState status;
  private String positionName;
  private String country;
  private String state;
  private String city;
  @Email
  private String contactEmail;
  private String detail;
  private List<@NotNull UUID> jobCategories;
}
