package muni.fi.cz.jobportal.api.common;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class CategoryDto {

  private UUID id;
  private String name;
  private List<ReferenceDto> occupations;
}
