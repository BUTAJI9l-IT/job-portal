package com.github.butaji9l.jobportal.be.exception;

import org.springframework.http.HttpStatus;

public class UploadFailedException extends JobPortalException {

  public UploadFailedException() {
    super("Error occurred during document uploading", HttpStatus.INTERNAL_SERVER_ERROR, null,
      "010");
  }

}
