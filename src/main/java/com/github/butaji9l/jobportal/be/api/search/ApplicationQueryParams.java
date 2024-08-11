package com.github.butaji9l.jobportal.be.api.search;

import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.APPLICANT;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.APPLICATION_DATE;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.COMPANY;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.JOB_POSITION;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.STATUS;

import com.github.butaji9l.jobportal.be.annotation.search.DateQueryField;
import com.github.butaji9l.jobportal.be.annotation.search.DateQueryField.RangeSide;
import com.github.butaji9l.jobportal.be.annotation.search.KeywordQueryField;
import com.github.butaji9l.jobportal.be.enums.ApplicationState;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class ApplicationQueryParams extends QueryParams {

  @KeywordQueryField(APPLICANT)
  private UUID applicant;
  @KeywordQueryField(JOB_POSITION)
  private UUID jobPosition;
  @KeywordQueryField(COMPANY)
  private UUID company;
  @KeywordQueryField(value = STATUS, generic = true)
  private ApplicationState status;
  @DateQueryField(APPLICATION_DATE)
  private Instant dateFrom;
  @DateQueryField(value = APPLICATION_DATE, side = RangeSide.TO)
  private Instant dateTo;

  @Override
  protected String[] queryIndices() {
    return new String[]{
      APPLICANT, JOB_POSITION, COMPANY
    };
  }
}
