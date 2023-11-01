package muni.fi.cz.jobportal.api.search;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import muni.fi.cz.jobportal.annotation.search.KeywordQueryField;
import muni.fi.cz.jobportal.enums.PositionState;

import java.util.List;
import java.util.UUID;

import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.*;

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
