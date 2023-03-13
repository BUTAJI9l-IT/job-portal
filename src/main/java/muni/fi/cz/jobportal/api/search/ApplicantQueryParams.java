package muni.fi.cz.jobportal.api.search;

import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.CITY;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.COUNTRY;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.JOB_POSITION;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.NAME;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.STATE;

import java.util.UUID;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import muni.fi.cz.jobportal.annotation.search.KeywordQueryField;

@Data
@SuperBuilder
public class ApplicantQueryParams extends QueryParams {

  @KeywordQueryField(JOB_POSITION)
  private UUID jobPosition;

  @Override
  public String[] queryIndices() {
    return new String[]{
      NAME, COUNTRY, STATE, CITY, JOB_POSITION
    };
  }
}
