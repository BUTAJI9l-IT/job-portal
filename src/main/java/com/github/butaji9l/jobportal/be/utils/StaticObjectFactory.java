package com.github.butaji9l.jobportal.be.utils;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;
import org.springframework.stereotype.Component;

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
