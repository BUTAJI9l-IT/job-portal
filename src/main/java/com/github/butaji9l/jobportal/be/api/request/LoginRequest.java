package com.github.butaji9l.jobportal.be.api.request;

import lombok.Data;

@Data
public class LoginRequest {

  private String email;
  private String password;

}
