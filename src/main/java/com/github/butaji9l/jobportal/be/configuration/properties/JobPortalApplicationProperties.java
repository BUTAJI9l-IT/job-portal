package com.github.butaji9l.jobportal.be.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "job-portal")
public class JobPortalApplicationProperties {

  private String baseUri;

  @NestedConfigurationProperty
  private KeyStoreProperties keyStore;

  @NestedConfigurationProperty
  private TokenProperties accessToken;

  @NestedConfigurationProperty
  private TokenProperties refreshToken;

  @NestedConfigurationProperty
  private EmailProperties notifications;
}
