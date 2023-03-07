package muni.fi.cz.jobportal.api.email;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import muni.fi.cz.jobportal.enums.ApplicationState;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class ApplicationEmailDto extends EmailDetailsDto {

  private ApplicationState state;
  private String jobPosition;
  private String company;
}
