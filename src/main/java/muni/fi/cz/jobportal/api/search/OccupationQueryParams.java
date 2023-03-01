
package muni.fi.cz.jobportal.api.search;

import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.NAME;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class OccupationQueryParams extends QueryParams {

  @Override
  public String[] queryIndices() {
    return new String[]{NAME};
  }
}
