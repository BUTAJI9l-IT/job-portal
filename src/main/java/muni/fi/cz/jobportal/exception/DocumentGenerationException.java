package muni.fi.cz.jobportal.exception;

import org.springframework.http.HttpStatus;

public class DocumentGenerationException extends JobPortalException {

  public DocumentGenerationException() {
    super("Error occurred during document generation", HttpStatus.INTERNAL_SERVER_ERROR, null, "009");
  }

}
