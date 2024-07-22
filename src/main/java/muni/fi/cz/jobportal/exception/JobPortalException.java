package muni.fi.cz.jobportal.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.spring.common.HttpStatusAdapter;

import java.net.URI;

/**
 * Abstract exception class.
 *
 * @author Vitalii Bortsov
 */
@Getter
public abstract class JobPortalException extends AbstractThrowableProblem {

  public static final String ROOT = "http://job-portal/";

  protected JobPortalException(String title, HttpStatus status, Object detail, String code) {
    super(URI.create(ROOT + code), title, new HttpStatusAdapter(status), detail == null ? "" : detail.toString());
  }
}
