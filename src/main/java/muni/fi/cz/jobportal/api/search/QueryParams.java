package muni.fi.cz.jobportal.api.search;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * Search parameters abstract class.
 *
 * @author Vitalii Bortsov
 */
@Data
@SuperBuilder
public abstract class QueryParams {

  private String q;

  /**
   * Method returns indexed field names with fulltext analyzers applied
   */
  public String[] queryIndices() {
    return new String[]{};
  }
}
