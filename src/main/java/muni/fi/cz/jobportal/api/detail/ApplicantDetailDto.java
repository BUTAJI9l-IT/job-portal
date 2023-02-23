package muni.fi.cz.jobportal.api.detail;

import java.util.List;
import java.util.UUID;
import lombok.Data;
import muni.fi.cz.jobportal.api.common.ExperienceDto;

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

  private List<ExperienceDto> experiences;
}
