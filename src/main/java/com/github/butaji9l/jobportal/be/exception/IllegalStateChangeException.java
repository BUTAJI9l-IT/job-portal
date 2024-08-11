package com.github.butaji9l.jobportal.be.exception;

import org.springframework.http.HttpStatus;

public class IllegalStateChangeException extends JobPortalException {

  public IllegalStateChangeException() {
    super("Illegal state transition", HttpStatus.BAD_REQUEST, null, "012");
  }

}
