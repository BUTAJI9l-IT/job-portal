package muni.fi.cz.jobportal.utils;

import java.time.Clock;
import java.time.ZoneId;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

@Component
public class StaticObjectFactory {
  private static final ZoneId ZONE = ZoneId.of("Europe/Prague");

  public Clock getClock() {
    return Clock.system(ZONE);
  }

  public String getSession() {
    return RequestContextHolder.currentRequestAttributes().getSessionId();
  }
}
