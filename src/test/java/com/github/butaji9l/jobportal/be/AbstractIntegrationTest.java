package com.github.butaji9l.jobportal.be;


import com.github.butaji9l.jobportal.be.annotation.IntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.TestcontainersConfiguration;

/**
 * Abstract Integration Test
 *
 * @author Vitalii Bortsov
 */
@IntegrationTest
public abstract class AbstractIntegrationTest extends AbstractTest {

  private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER;

  static {
    try (PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:13.1-alpine")) {
      POSTGRESQL_CONTAINER = container
        .withReuse(true)
        .withDatabaseName("integration-tests-db")
        .withUsername("test-database")
        .withPassword("test-database-pwd");
    }
  }

  @BeforeAll
  public static void beforeAll() {
    POSTGRESQL_CONTAINER.start();
  }

  @DynamicPropertySource
  private static void initialize(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
    registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
    registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
    TestcontainersConfiguration.getInstance()
      .updateUserConfig("testcontainers.reuse.enable", "true");
  }
}
