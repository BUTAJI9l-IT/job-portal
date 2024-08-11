package com.github.butaji9l.jobportal.be.service;

import com.github.butaji9l.jobportal.be.api.common.CompanyDto;
import com.github.butaji9l.jobportal.be.api.detail.CompanyDetailDto;
import com.github.butaji9l.jobportal.be.api.request.CompanyCreateDto;
import com.github.butaji9l.jobportal.be.api.request.CompanyUpdateDto;
import com.github.butaji9l.jobportal.be.api.search.CompanyQueryParams;

public interface CompanyService extends
  CRUDService<CompanyCreateDto, CompanyDto, CompanyUpdateDto, CompanyDetailDto, CompanyQueryParams> {

}
