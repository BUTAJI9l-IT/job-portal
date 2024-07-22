package muni.fi.cz.jobportal.utils;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;

@Component
public class StaticObjectFactory {

  private static final ZoneId ZONE = ZoneId.systemDefault();

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

}
