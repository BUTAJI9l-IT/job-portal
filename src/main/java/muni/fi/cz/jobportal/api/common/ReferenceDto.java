package muni.fi.cz.jobportal.api.common;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReferenceDto {

  private UUID id;
  private String name;
}
