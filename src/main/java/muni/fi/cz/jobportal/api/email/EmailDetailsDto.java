package muni.fi.cz.jobportal.api.email;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class EmailDetailsDto {

  private String recipient;
}
