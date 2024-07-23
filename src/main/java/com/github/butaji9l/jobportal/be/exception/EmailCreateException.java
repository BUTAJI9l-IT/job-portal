package com.github.butaji9l.jobportal.be.exception;

import org.springframework.http.HttpStatus;

public class EmailCreateException extends JobPortalException {

  public EmailCreateException() {
    super("Error occurred during email parsing", HttpStatus.INTERNAL_SERVER_ERROR, null, "013");
  }

}
