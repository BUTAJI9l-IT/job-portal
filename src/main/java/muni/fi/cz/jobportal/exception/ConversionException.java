package muni.fi.cz.jobportal.exception;

import org.springframework.http.HttpStatus;

public class ConversionException extends JobPortalException {

  public ConversionException() {
    super("Error occurred during conversion", HttpStatus.INTERNAL_SERVER_ERROR, null, "008");
  }

}
