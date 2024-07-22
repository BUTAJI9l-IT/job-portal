package muni.fi.cz.jobportal.api.common;

import lombok.Data;

import java.util.UUID;

@Data
public class ApplicantDto {

  private UUID id;
  private UUID userId;
  private String name;
  private String lastName;
  private String email;
}
