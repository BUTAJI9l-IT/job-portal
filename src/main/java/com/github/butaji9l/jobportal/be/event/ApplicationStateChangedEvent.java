package com.github.butaji9l.jobportal.be.event;

import com.github.butaji9l.jobportal.be.enums.ApplicationState;
import java.util.UUID;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event for application status changes.
 *
 * @author Vitalii Bortsov
 */
@Getter
public class ApplicationStateChangedEvent extends ApplicationEvent {

  private final UUID application;
  private final ApplicationState status;

  private ApplicationStateChangedEvent(Object source, UUID application, ApplicationState status) {
    super(source);
    this.application = application;
    this.status = status;
  }

  public static ApplicationStateChangedEvent toDeclined(Object source, UUID application) {
    return new ApplicationStateChangedEvent(source, application, ApplicationState.DECLINED);
  }

  public static ApplicationStateChangedEvent toApproved(Object source, UUID application) {
    return new ApplicationStateChangedEvent(source, application, ApplicationState.APPROVED);
  }
}
