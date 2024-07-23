package com.github.butaji9l.jobportal.be.api.search;

import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.CATEGORY;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.CITY;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.COMPANY;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.COUNTRY;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.DESCRIPTION;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.NAME;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.STATE;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.STATUS;

import com.github.butaji9l.jobportal.be.annotation.search.KeywordQueryField;
import com.github.butaji9l.jobportal.be.enums.PositionState;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class JobPositionQueryParams extends QueryParams {

  @KeywordQueryField(value = STATUS, generic = true)
  private PositionState status;
  @KeywordQueryField(COMPANY)
  private List<UUID> company;
  @KeywordQueryField(CATEGORY)
  private List<UUID> category;

  @Override
  public String[] queryIndices() {
    return new String[]{
      NAME, DESCRIPTION, COUNTRY, CITY, STATE, COMPANY
    };
  }
}
