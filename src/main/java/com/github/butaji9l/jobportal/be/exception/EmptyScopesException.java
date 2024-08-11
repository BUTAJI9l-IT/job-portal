package com.github.butaji9l.jobportal.be.exception;

import org.springframework.http.HttpStatus;

public class EmptyScopesException extends JobPortalException {

  public EmptyScopesException() {
    super("User has empty scopes!", HttpStatus.INTERNAL_SERVER_ERROR, null, "001");
  }
}
