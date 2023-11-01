package muni.fi.cz.jobportal.api.search;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Arrays;
import java.util.List;

import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.FULLTEXT_SUFFIX;

/**
 * Search parameters abstract class.
 *
 * @author Vitalii Bortsov
 */
@Data
@SuperBuilder
public abstract class QueryParams {

  private List<String> qList;

  /**
   * Method returns indexed field names with fulltext analyzers applied
   */
  protected String[] queryIndices() {
    return new String[]{};
  }

  public String[] getQueryIndices() {
    return Arrays.stream(queryIndices()).map(field -> field + FULLTEXT_SUFFIX).toArray(String[]::new);
  }
}
