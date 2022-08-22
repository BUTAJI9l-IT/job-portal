package muni.fi.cz.jobportal.configuration.properties;

import lombok.Data;
import org.springframework.core.io.Resource;

@Data
public class KeyStoreProperties {

  private String keyStorePassword;

  private String keyPairName;

  private String keyPairPassword;

  private String kid;

  private Resource location;
}
