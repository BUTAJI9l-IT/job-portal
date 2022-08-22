package muni.fi.cz.jobportal.configuration.properties;

import java.time.Duration;
import lombok.Data;

@Data
abstract class TokenProperties {
  private Duration duration;
}
