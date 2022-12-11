package muni.fi.cz.jobportal.exception;

import org.springframework.http.HttpStatus;

public class PasswordMissmatchException extends JobPortalException {

  public PasswordMissmatchException() {
    super("Passwords do not match!", HttpStatus.BAD_REQUEST, null, "002");
  }
}
