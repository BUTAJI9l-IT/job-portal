package muni.fi.cz.jobportal.configuration;

import static muni.fi.cz.jobportal.configuration.constants.ApplicationConstants.BEARER_AUTH;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Properties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Swagger OpenAPI.
 *
 * @author Vitalii Bortsov
 */
@Configuration
public class SwaggerConfiguration {

  @Bean
  public OpenAPI openAPI(BuildProperties buildProperties) {
    final var component = new Components()
        .addSecuritySchemes(BEARER_AUTH, new SecurityScheme().type(Type.HTTP).scheme("Bearer"));

    final var info = new Info().title(buildProperties.getArtifact()).version(buildProperties.getVersion());

    return new OpenAPI().info(info).components(component)
        .addServersItem(new Server().url("/").description("xx"));
  }

  @Bean
  @ConditionalOnMissingBean(BuildProperties.class)
  public BuildProperties buildPropertiesNoInstall() {
    final var props = new Properties();
    props.setProperty("artifact", "job-portal");
    props.setProperty("version", "local");

    return new BuildProperties(props);
  }
}
