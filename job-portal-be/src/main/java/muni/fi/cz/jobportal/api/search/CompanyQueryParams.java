package muni.fi.cz.jobportal.api.search;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import muni.fi.cz.jobportal.annotation.search.KeywordQueryField;
import muni.fi.cz.jobportal.enums.CompanyNumberOfEmployees;

import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.*;

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
