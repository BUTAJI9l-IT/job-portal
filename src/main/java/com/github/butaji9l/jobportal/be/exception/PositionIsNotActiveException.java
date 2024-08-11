package com.github.butaji9l.jobportal.be.exception;

import org.springframework.http.HttpStatus;

public class PositionIsNotActiveException extends JobPortalException {

  public PositionIsNotActiveException() {
    super("Job Position is not active", HttpStatus.BAD_REQUEST, null, "006");
  }
}
