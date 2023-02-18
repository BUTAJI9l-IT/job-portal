package muni.fi.cz.jobportal.api.detail;

import java.util.UUID;
import lombok.Data;

@Data
public class ApplicantDetailDto {

  private UUID id;

  private String name;
  private String lastName;
  private String email;

  private String country;
  private String state;
  private String city;
  private String profile;

}
