package com.github.butaji9l.jobportal.be.exception;

import org.springframework.http.HttpStatus;

public class OldPasswordMismatchException extends JobPortalException {

  public OldPasswordMismatchException() {
    super("Old password does not match", HttpStatus.BAD_REQUEST, null, "007");
  }
}
