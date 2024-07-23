package com.github.butaji9l.jobportal.be.api.request;

import lombok.Data;

@Data
public class ApplicantUpdateDto {

  private String name;
  private String lastName;

  private String country;
  private String state;
  private String city;
  private String phone;
  private String profile;

}
