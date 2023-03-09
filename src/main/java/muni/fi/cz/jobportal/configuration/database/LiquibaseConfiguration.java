package muni.fi.cz.jobportal.configuration.database;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * LiquibaseConfiguration for the application.
 *
 * @author Vitalii Bortsov
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnMissingBean(SpringLiquibase.class)
@EnableConfigurationProperties({DataSourceProperties.class, LiquibaseProperties.class})
public class LiquibaseConfiguration extends LiquibaseAutoConfiguration.LiquibaseConfiguration {

  public LiquibaseConfiguration(LiquibaseProperties properties) {
    super(properties);
  }
}
