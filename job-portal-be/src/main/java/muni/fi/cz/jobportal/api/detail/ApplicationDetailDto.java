package muni.fi.cz.jobportal.api.detail;

import lombok.Data;
import muni.fi.cz.jobportal.enums.ApplicationState;

import java.time.Instant;
import java.util.UUID;

@Data
public class ApplicationDetailDto {

  private UUID id;
  private ApplicationState state;
  private Instant date;
  private ApplicantDetailDto applicant;
  private JobPositionDetailDto jobPosition;
}
