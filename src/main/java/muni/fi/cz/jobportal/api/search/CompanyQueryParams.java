package muni.fi.cz.jobportal.api.search;

import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.COMPANY_SIZE;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.DESCRIPTION;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.NAME;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import muni.fi.cz.jobportal.annotation.search.KeywordQueryField;
import muni.fi.cz.jobportal.enums.CompanyNumberOfEmployees;

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
