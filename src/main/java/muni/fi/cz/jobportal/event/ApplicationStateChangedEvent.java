package muni.fi.cz.jobportal.event;

import java.util.UUID;
import lombok.Getter;
import muni.fi.cz.jobportal.enums.ApplicationState;
import org.springframework.context.ApplicationEvent;

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
