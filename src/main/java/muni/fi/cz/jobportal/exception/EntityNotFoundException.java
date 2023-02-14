package muni.fi.cz.jobportal.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends JobPortalException {

  public EntityNotFoundException(Class<?> clazz) {
    super("Entity is not found", HttpStatus.NOT_FOUND, "Entity of class " + clazz.getSimpleName() + " is not found",
      "004");
  }

}
