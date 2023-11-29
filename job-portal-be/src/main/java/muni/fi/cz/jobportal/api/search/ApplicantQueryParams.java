package muni.fi.cz.jobportal.api.search;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import muni.fi.cz.jobportal.annotation.search.KeywordQueryField;

import java.util.UUID;

import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.*;

@EqualsAndHashCode(callSuper = true)
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
