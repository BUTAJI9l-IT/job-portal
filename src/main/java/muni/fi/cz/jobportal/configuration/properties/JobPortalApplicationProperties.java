package muni.fi.cz.jobportal.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "job-portal")
public class JobPortalApplicationProperties {

  @NestedConfigurationProperty
  private KeyStoreProperties keyStore;

  @NestedConfigurationProperty
  private TokenProperties accessToken;

  @NestedConfigurationProperty
  private TokenProperties refreshToken;
}
