package muni.fi.cz.jobportal.api.search;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import muni.fi.cz.jobportal.annotation.search.KeywordQueryField;
import muni.fi.cz.jobportal.enums.JobPortalScope;

import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.*;

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
