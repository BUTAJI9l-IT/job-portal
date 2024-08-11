package com.github.butaji9l.jobportal.be.api.detail;

import com.github.butaji9l.jobportal.be.api.common.ExperienceDto;
import java.util.List;
import java.util.UUID;
import lombok.Data;

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
