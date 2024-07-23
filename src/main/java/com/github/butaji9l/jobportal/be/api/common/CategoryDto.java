package com.github.butaji9l.jobportal.be.api.common;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class CategoryDto {

  private UUID id;
  private String name;
  private List<ReferenceDto> occupations;
}
