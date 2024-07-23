package muni.fi.cz.jobportal.api.detail;

import java.time.Instant;
import java.util.UUID;
import lombok.Data;
import muni.fi.cz.jobportal.enums.ApplicationState;

@Data
public class ApplicationDetailDto {

  private UUID id;
  private ApplicationState state;
  private Instant date;
  private ApplicantDetailDto applicant;
  private JobPositionDetailDto jobPosition;
}
