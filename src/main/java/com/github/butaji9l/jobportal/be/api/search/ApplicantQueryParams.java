package com.github.butaji9l.jobportal.be.api.search;

import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.CITY;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.COUNTRY;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.JOB_POSITION;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.NAME;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.STATE;

import com.github.butaji9l.jobportal.be.annotation.search.KeywordQueryField;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

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
