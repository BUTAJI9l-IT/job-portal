package muni.fi.cz.jobportal.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
    enableDefaultTransactions = false,
    basePackages = {"muni.fi.cz.jobportal.repository"}
)
public class GlobalConfiguration {

}
