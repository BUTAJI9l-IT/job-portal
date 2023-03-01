package muni.fi.cz.jobportal.repository.search;

import muni.fi.cz.jobportal.api.search.QueryParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface SearchRepository<T, Q extends QueryParams> {

  Page<T> search(Pageable pageable, Q params);

  Class<T> getBaseClass();
}
