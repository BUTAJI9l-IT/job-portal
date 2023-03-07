package muni.fi.cz.jobportal.exception;

import org.springframework.http.HttpStatus;

public class ApplicationAlreadyExistsException extends JobPortalException {

  public ApplicationAlreadyExistsException() {
    super("Current user already applied for the job", HttpStatus.BAD_REQUEST, null, "011");
  }

}
