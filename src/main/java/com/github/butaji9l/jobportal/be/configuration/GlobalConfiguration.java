package com.github.butaji9l.jobportal.be.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Global configuration class.
 *
 * @author Vitalii Bortsov
 */
@Configuration
@EnableJpaRepositories(
  enableDefaultTransactions = false,
  basePackages = {"com.github.butaji9l.jobportal.be.repository"}
)
@EnableScheduling
public class GlobalConfiguration {

}
