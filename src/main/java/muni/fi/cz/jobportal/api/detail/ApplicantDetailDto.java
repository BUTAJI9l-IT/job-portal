package muni.fi.cz.jobportal.api.detail;

import lombok.Data;
import muni.fi.cz.jobportal.api.common.ExperienceDto;

import java.util.List;
import java.util.UUID;

@Data
public class ApplicantDetailDto {

  private UUID id;
  private UUID userId;
  private String name;
  private String lastName;
  private String email;

  private String country;
  private String state;
  private String city;
  private String profile;
  private String phone;

  private List<ExperienceDto> experiences;
}
