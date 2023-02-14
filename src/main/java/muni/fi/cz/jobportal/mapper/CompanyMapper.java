package muni.fi.cz.jobportal.mapper;

import muni.fi.cz.jobportal.api.detail.CompanyDetailDto;
import muni.fi.cz.jobportal.api.request.CompanyCreateDto;
import muni.fi.cz.jobportal.domain.Company;
import muni.fi.cz.jobportal.factory.CompanyFactory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = CompanyFactory.class)
public interface CompanyMapper {

  @Mapping(target = "user", ignore = true)
  Company map(CompanyCreateDto request);

  CompanyDetailDto map(Company source);

}
