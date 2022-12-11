package muni.fi.cz.jobportal.exception;

import java.net.URI;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.spring.common.HttpStatusAdapter;

@Getter
public abstract class JobPortalException extends AbstractThrowableProblem {

  private final transient Object originalProblem;
  public static final String ROOT = "http://iseo-dms-service/";

  protected JobPortalException(String title, HttpStatus status, Object detail, String code) {
    super(URI.create(ROOT + code), title, new HttpStatusAdapter(status), null);
    this.originalProblem = detail;
  }
}
