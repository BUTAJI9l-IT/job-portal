package muni.fi.cz.jobportal.api.common;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CategoryDto {

  private UUID id;
  private String name;
  private List<ReferenceDto> occupations;
}
