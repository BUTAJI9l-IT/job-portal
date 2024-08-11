package com.github.butaji9l.jobportal.be.exception;

import org.springframework.http.HttpStatus;

public class ApplicationAlreadyExistsException extends JobPortalException {

  public ApplicationAlreadyExistsException() {
    super("Current user already applied for the job", HttpStatus.BAD_REQUEST, null, "011");
  }

}
