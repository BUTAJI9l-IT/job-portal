package muni.fi.cz.jobportal.api.search;

import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.APPLICANT;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.APPLICANT_NAME;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.APPLICATION_DATE;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.COMPANY;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.COMPANY_NAME;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.JOB_POSITION;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.JOB_POSITION_NAME;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.STATUS;

import java.time.Instant;
import java.util.UUID;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import muni.fi.cz.jobportal.annotation.search.DateQueryField;
import muni.fi.cz.jobportal.annotation.search.DateQueryField.RangeSide;
import muni.fi.cz.jobportal.annotation.search.KeywordQueryField;
import muni.fi.cz.jobportal.enums.ApplicationState;

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
  public String[] queryIndices() {
    return new String[]{
      APPLICANT_NAME, JOB_POSITION_NAME, COMPANY_NAME
    };
  }
}
