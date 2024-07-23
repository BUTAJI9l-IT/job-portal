package muni.fi.cz.jobportal.repository.search;

import muni.fi.cz.jobportal.api.search.QueryParams;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Search repository interface.
 *
 * @author Vitalii Bortsov
 */
public interface SearchRepository<T, Q extends QueryParams> extends InitializingBean {

  /**
   * Searches for entities based on search parameters.
   *
   * @param pageable pageable parameter
   * @param params   search parameters
   * @return pageable model for the base entity of this repository that contains all the entities,
   * which have matched search parameters
   */
  Page<T> search(Pageable pageable, Q params);

  /**
   * Returns repository's base class.
   */
  Class<T> getBaseClass();
}
