package muni.fi.cz.jobportal.api.search;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import muni.fi.cz.jobportal.annotation.search.DateQueryField;
import muni.fi.cz.jobportal.annotation.search.DateQueryField.RangeSide;
import muni.fi.cz.jobportal.annotation.search.KeywordQueryField;
import muni.fi.cz.jobportal.enums.ApplicationState;

import java.time.Instant;
import java.util.UUID;

import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.*;

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
