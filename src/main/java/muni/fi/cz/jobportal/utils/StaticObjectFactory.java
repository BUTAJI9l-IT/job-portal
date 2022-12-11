package muni.fi.cz.jobportal.utils;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

@Component
public class StaticObjectFactory {

  private static final ZoneId ZONE = ZoneId.of("Europe/Prague");

  public Instant now() {
    return Instant.now(getClock());
  }

  public Clock getClock() {
    return Clock.system(ZONE);
  }

  public Instant getNowAsInstant() {
    return Instant.now(getClock());
  }

  public UUID getRandomId() {
    return UUID.randomUUID();
  }

  public String getSession() {
    return RequestContextHolder.currentRequestAttributes().getSessionId();
  }
}
