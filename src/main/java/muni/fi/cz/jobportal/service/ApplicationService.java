package muni.fi.cz.jobportal.service;

import java.io.ByteArrayInputStream;
import muni.fi.cz.jobportal.api.common.ApplicationDto;
import muni.fi.cz.jobportal.api.request.ApplicationCreateDto;
import muni.fi.cz.jobportal.api.request.ApplicationUpdateDto;
import muni.fi.cz.jobportal.api.search.ApplicationQueryParams;
import org.springframework.lang.NonNull;

public interface ApplicationService extends
  CRUDService<ApplicationCreateDto, ApplicationDto, ApplicationUpdateDto, ApplicationDto, ApplicationQueryParams> {

  @NonNull
  ByteArrayInputStream generateCV();
}
