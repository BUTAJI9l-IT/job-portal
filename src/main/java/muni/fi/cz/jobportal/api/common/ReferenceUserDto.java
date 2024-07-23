package muni.fi.cz.jobportal.api.common;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReferenceUserDto {

  private UUID id;
  private String name;
  private UUID userId;
}
