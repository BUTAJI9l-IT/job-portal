package muni.fi.cz.jobportal.configuration.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Class with indexed fields' names.
 *
 * @author Vitalii Bortsov
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchProperties {

  public static final String SORT_SUFFIX = "_sort";
  public static final String FULLTEXT_SUFFIX = "_ft";
  public static final String STATUS = "status";
  public static final String NAME = "name";
  public static final String DESCRIPTION = "description";
  public static final String COUNTRY = "country";
  public static final String CITY = "city";
  public static final String STATE = "state";
  public static final String EMAIL = "email";
  public static final String COMPANY = "company";
  public static final String CATEGORY = "category";
  public static final String APPLICANT = "applicant";
  public static final String JOB_POSITION = "jobPosition";
  public static final String COMPANY_SIZE = "companySize";
  public static final String USER_SCOPE = "scope";
  public static final String APPLICATION_DATE = "date";
  public static final String RELEVANCE = "created";
}
