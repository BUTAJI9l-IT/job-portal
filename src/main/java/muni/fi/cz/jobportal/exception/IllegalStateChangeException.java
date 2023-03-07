package muni.fi.cz.jobportal.exception;

import org.springframework.http.HttpStatus;

public class IllegalStateChangeException extends JobPortalException {

  public IllegalStateChangeException() {
    super("Illegal state transition", HttpStatus.BAD_REQUEST, null, "012");
  }

}
