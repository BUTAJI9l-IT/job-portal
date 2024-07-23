package com.github.butaji9l.jobportal.be.repository.search;

import com.github.butaji9l.jobportal.be.api.search.OccupationQueryParams;
import com.github.butaji9l.jobportal.be.domain.Occupation;

public interface OccupationSearchRepository extends
  SearchRepository<Occupation, OccupationQueryParams> {

}
