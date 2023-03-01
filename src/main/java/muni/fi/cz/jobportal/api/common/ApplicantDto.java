package muni.fi.cz.jobportal.api.common;

import java.util.UUID;
import lombok.Data;

@Data
public class ApplicantDto {

  private UUID id;

  private String name;
  private String lastName;
  private String email;
}
