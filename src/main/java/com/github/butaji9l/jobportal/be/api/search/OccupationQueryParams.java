package com.github.butaji9l.jobportal.be.api.search;

import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.NAME;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class OccupationQueryParams extends QueryParams {

  @Override
  public String[] queryIndices() {
    return new String[]{NAME};
  }
}
