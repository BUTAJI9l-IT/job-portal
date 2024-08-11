package com.github.butaji9l.jobportal.be.exception;

import org.springframework.http.HttpStatus;

public class JobPositionAlreadySavedException extends JobPortalException {

  public JobPositionAlreadySavedException() {
    super("Job Position is already saved", HttpStatus.BAD_REQUEST, null, "004");
  }

}

