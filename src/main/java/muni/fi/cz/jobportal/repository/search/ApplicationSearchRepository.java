package muni.fi.cz.jobportal.repository.search;

import muni.fi.cz.jobportal.api.search.ApplicationQueryParams;
import muni.fi.cz.jobportal.domain.Application;

public interface ApplicationSearchRepository extends
  SearchRepository<Application, ApplicationQueryParams> {

}
