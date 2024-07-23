package com.github.butaji9l.jobportal.be.api.search;

import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.COMPANY_SIZE;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.DESCRIPTION;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.NAME;

import com.github.butaji9l.jobportal.be.annotation.search.KeywordQueryField;
import com.github.butaji9l.jobportal.be.enums.CompanyNumberOfEmployees;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class CompanyQueryParams extends QueryParams {

  @KeywordQueryField(value = COMPANY_SIZE, generic = true)
  private CompanyNumberOfEmployees companySize;

  @Override
  public String[] queryIndices() {
    return new String[]{
      NAME, DESCRIPTION
    };
  }
}
