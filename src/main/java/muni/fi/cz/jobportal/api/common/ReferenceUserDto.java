package muni.fi.cz.jobportal.api.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReferenceUserDto {

  private UUID id;
  private String name;
  private UUID userId;
}
