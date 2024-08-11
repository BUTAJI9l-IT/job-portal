package com.github.butaji9l.jobportal.be.api.common;

import java.util.UUID;
import lombok.Data;

@Data
public class ApplicantDto {

  private UUID id;
  private UUID userId;
  private String name;
  private String lastName;
  private String email;
}
