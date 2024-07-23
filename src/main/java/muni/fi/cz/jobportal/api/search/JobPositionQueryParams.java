package muni.fi.cz.jobportal.api.search;

import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.CATEGORY;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.CITY;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.COMPANY;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.COUNTRY;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.DESCRIPTION;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.NAME;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.STATE;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.STATUS;

import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import muni.fi.cz.jobportal.annotation.search.KeywordQueryField;
import muni.fi.cz.jobportal.enums.PositionState;

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
