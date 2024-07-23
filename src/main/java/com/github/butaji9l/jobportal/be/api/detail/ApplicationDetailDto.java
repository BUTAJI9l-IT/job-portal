package com.github.butaji9l.jobportal.be.api.detail;

import com.github.butaji9l.jobportal.be.enums.ApplicationState;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class ApplicationDetailDto {

  private UUID id;
  private ApplicationState state;
  private Instant date;
  private ApplicantDetailDto applicant;
  private JobPositionDetailDto jobPosition;
}
