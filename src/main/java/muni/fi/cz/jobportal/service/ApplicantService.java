package muni.fi.cz.jobportal.service;

import muni.fi.cz.jobportal.api.common.ApplicantDto;
import muni.fi.cz.jobportal.api.detail.ApplicantDetailDto;
import muni.fi.cz.jobportal.api.request.ApplicantCreateDto;
import muni.fi.cz.jobportal.api.request.ApplicantUpdateDto;
import muni.fi.cz.jobportal.api.search.ApplicantQueryParams;

public interface ApplicantService extends
  CRUDService<ApplicantCreateDto, ApplicantDto, ApplicantUpdateDto, ApplicantDetailDto, ApplicantQueryParams> {

}
