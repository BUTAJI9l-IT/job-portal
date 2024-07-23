package com.github.butaji9l.jobportal.be.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyRegisteredException extends JobPortalException {

  public UserAlreadyRegisteredException(String email) {
    super("User exists", HttpStatus.BAD_REQUEST, "User with email " + email + " already exists",
      "002");
  }

}
