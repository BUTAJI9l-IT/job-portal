package com.github.butaji9l.jobportal.be.exception;

import org.springframework.http.HttpStatus;

public class ConversionException extends JobPortalException {

  public ConversionException() {
    super("Error occurred during conversion", HttpStatus.INTERNAL_SERVER_ERROR, null, "008");
  }

}
