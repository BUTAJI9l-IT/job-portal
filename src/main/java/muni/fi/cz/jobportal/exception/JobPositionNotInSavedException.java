package muni.fi.cz.jobportal.exception;

import org.springframework.http.HttpStatus;

public class JobPositionNotInSavedException extends JobPortalException {

  public JobPositionNotInSavedException() {
    super("Job Position is not in saved jobs", HttpStatus.BAD_REQUEST, null, "005");
  }

}

