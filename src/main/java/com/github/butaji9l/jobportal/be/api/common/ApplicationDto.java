package com.github.butaji9l.jobportal.be.api.common;

import com.github.butaji9l.jobportal.be.enums.ApplicationState;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class ApplicationDto {

  private UUID id;

  private ApplicationState state;
  private Instant date;
  private ReferenceDto applicant;
  private ReferenceDto jobPosition;
}
