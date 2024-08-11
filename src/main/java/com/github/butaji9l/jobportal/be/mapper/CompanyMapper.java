package com.github.butaji9l.jobportal.be.mapper;

import com.github.butaji9l.jobportal.be.api.common.CompanyDto;
import com.github.butaji9l.jobportal.be.api.detail.CompanyDetailDto;
import com.github.butaji9l.jobportal.be.api.request.CompanyCreateDto;
import com.github.butaji9l.jobportal.be.api.request.CompanyUpdateDto;
import com.github.butaji9l.jobportal.be.api.request.UserCreateDto;
import com.github.butaji9l.jobportal.be.domain.Company;
import com.github.butaji9l.jobportal.be.domain.User;
import com.github.butaji9l.jobportal.be.factory.CompanyFactory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper for companies
 *
 * @author Vitalii Bortsov
 */
@Mapper(uses = {CompanyFactory.class, JobPositionMapper.class})
public interface CompanyMapper {

  @Mapping(target = "user", ignore = true)
  Company map(CompanyCreateDto request);

  @Mapping(target = "email", source = "user.email")
  @Mapping(target = "userId", source = "user.id")
  CompanyDetailDto map(Company source);

  @Mapping(target = "userId", source = "user.id")
  CompanyDto mapDto(Company source);

  @Mapping(source = "name", target = "user.name")
  @Mapping(source = "lastName", target = "user.lastName")
  Company update(@MappingTarget Company target, CompanyUpdateDto source);

  @Mapping(target = "user", source = "created.id")
  CompanyCreateDto map(UserCreateDto request, User created);

}
