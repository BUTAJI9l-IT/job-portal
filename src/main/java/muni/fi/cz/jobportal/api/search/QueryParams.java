package muni.fi.cz.jobportal.api.search;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class QueryParams {

  private String q;

  public String[] queryIndices() {
    return new String[]{};
  }
}
