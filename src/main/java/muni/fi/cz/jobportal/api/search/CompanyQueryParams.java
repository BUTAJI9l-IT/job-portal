package muni.fi.cz.jobportal.api.search;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class CompanyQueryParams extends QueryParams {

  private String bb;
}
