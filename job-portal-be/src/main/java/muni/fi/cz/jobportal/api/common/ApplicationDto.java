package muni.fi.cz.jobportal.api.common;

import lombok.Data;
import muni.fi.cz.jobportal.enums.ApplicationState;

import java.time.Instant;
import java.util.UUID;

@Data
public class ApplicationDto {

  private UUID id;

  private ApplicationState state;
  private Instant date;
  private ReferenceDto applicant;
  private ReferenceDto jobPosition;
}
