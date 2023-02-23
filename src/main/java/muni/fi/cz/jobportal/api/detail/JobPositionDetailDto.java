package muni.fi.cz.jobportal.api.detail;

import java.time.Instant;
import lombok.Data;
import muni.fi.cz.jobportal.api.common.JobPositionDto;

@Data
public class JobPositionDetailDto extends JobPositionDto {

  private String country;
  private String state;
  private String city;

  private String contactEmail;
  private String detail;

  private Integer appliedCount;

  private Instant created;
  private Instant lastUpdated;
}
