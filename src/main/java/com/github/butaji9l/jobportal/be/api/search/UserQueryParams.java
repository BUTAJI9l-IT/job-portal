package com.github.butaji9l.jobportal.be.api.search;

import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.EMAIL;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.NAME;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.USER_SCOPE;

import com.github.butaji9l.jobportal.be.annotation.search.KeywordQueryField;
import com.github.butaji9l.jobportal.be.enums.JobPortalScope;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

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
