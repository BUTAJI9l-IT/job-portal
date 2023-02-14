package muni.fi.cz.jobportal.service;

import muni.fi.cz.jobportal.api.common.CompanyDto;
import muni.fi.cz.jobportal.api.detail.CompanyDetailDto;
import muni.fi.cz.jobportal.api.request.CompanyCreateDto;
import muni.fi.cz.jobportal.api.request.CompanyUpdateDto;
import muni.fi.cz.jobportal.api.search.CompanyQueryParams;

public interface CompanyService extends
  CRUDService<CompanyCreateDto, CompanyDto, CompanyUpdateDto, CompanyDetailDto, CompanyQueryParams> {

}
