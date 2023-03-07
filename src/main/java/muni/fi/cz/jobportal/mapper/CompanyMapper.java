package muni.fi.cz.jobportal.mapper;

import muni.fi.cz.jobportal.api.common.CompanyDto;
import muni.fi.cz.jobportal.api.detail.CompanyDetailDto;
import muni.fi.cz.jobportal.api.request.CompanyCreateDto;
import muni.fi.cz.jobportal.api.request.CompanyUpdateDto;
import muni.fi.cz.jobportal.api.request.UserCreateDto;
import muni.fi.cz.jobportal.domain.Company;
import muni.fi.cz.jobportal.domain.User;
import muni.fi.cz.jobportal.factory.CompanyFactory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = {CompanyFactory.class, JobPositionMapper.class})
public interface CompanyMapper {

  @Mapping(target = "user", ignore = true)
  Company map(CompanyCreateDto request);

  @Mapping(target = "email", source = "user.email")
  @Mapping(target = "userId", source = "user.id")
  CompanyDetailDto map(Company source);

  @Mapping(target = "userId", source = "user.id")
  CompanyDto mapDto(Company source);

  Company update(@MappingTarget Company target, CompanyUpdateDto source);

  @Mapping(target = "user", source = "created.id")
  CompanyCreateDto map(UserCreateDto request, User created);

}
