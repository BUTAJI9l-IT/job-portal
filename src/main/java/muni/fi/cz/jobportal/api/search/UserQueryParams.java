package muni.fi.cz.jobportal.api.search;

import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.EMAIL;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.NAME;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.USER_SCOPE;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import muni.fi.cz.jobportal.annotation.search.KeywordQueryField;
import muni.fi.cz.jobportal.enums.JobPortalScope;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class UserQueryParams extends QueryParams {

  @KeywordQueryField(value = USER_SCOPE, generic = true)
  private JobPortalScope scope;

  @Override
  public String[] queryIndices() {
    return new String[]{
      NAME, EMAIL
    };
  }
}
