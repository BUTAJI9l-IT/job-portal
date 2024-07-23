package com.github.butaji9l.jobportal.be.service;

import com.github.butaji9l.jobportal.be.api.common.ApplicationDto;
import com.github.butaji9l.jobportal.be.api.detail.ApplicationDetailDto;
import com.github.butaji9l.jobportal.be.api.request.ApplicationCreateDto;
import com.github.butaji9l.jobportal.be.api.request.ApplicationUpdateDto;
import com.github.butaji9l.jobportal.be.api.search.ApplicationQueryParams;

public interface ApplicationService extends
  CRUDService<ApplicationCreateDto, ApplicationDto, ApplicationUpdateDto, ApplicationDetailDto, ApplicationQueryParams> {

}
