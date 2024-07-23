package muni.fi.cz.jobportal.configuration;

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
  basePackages = {"muni.fi.cz.jobportal.repository"}
)
@EnableScheduling
public class GlobalConfiguration {

}
