package muni.fi.cz.jobportal;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Abstract Integration Test
 * <p>
 * source: <a
 * href="https://jschmitz.dev/posts/testcontainers_how_to_use_them_in_your_spring_boot_integration_tests/">...</a>
 * </p>
 */
@Testcontainers
@ContextConfiguration(initializers = AbstractIntegrationTest.DataSourceInitializer.class)
public abstract class AbstractIntegrationTest extends AbstractTest {

  @Container
  private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:13.1-alpine");

  public static class DataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
      TestPropertySourceUtils.addInlinedPropertiesToEnvironment(applicationContext,
          "spring.test.database.replace=none",
          "spring.datasource.url=" + postgresContainer.getJdbcUrl(),
          "spring.datasource.username=" + postgresContainer.getUsername(),
          "spring.datasource.password=" + postgresContainer.getPassword()
      );
    }
  }

}
