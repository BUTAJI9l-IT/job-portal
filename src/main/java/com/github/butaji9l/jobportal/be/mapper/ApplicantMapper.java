package com.github.butaji9l.jobportal.be.mapper;

import com.github.butaji9l.jobportal.be.api.common.ApplicantDto;
import com.github.butaji9l.jobportal.be.api.detail.ApplicantDetailDto;
import com.github.butaji9l.jobportal.be.api.request.ApplicantCreateDto;
import com.github.butaji9l.jobportal.be.api.request.ApplicantUpdateDto;
import com.github.butaji9l.jobportal.be.domain.Applicant;
import com.github.butaji9l.jobportal.be.factory.ApplicantFactory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper for applicants
 *
 * @author Vitalii Bortsov
 */
@Mapper(uses = {ApplicantFactory.class, ExperienceMapper.class})
public interface ApplicantMapper {

  @Mapping(target = "user", ignore = true)
  Applicant map(ApplicantCreateDto request);

  @Mapping(target = "name", source = "user.name")
  @Mapping(target = "lastName", source = "user.lastName")
  @Mapping(target = "email", source = "user.email")
  @Mapping(target = "userId", source = "user.id")
  ApplicantDetailDto map(Applicant source);

  @Mapping(target = "name", source = "user.name")
  @Mapping(target = "lastName", source = "user.lastName")
  @Mapping(target = "email", source = "user.email")
  @Mapping(target = "userId", source = "user.id")
  ApplicantDto mapDto(Applicant source);

  @Mapping(source = "name", target = "user.name")
  @Mapping(source = "lastName", target = "user.lastName")
  Applicant update(@MappingTarget Applicant applicant, ApplicantUpdateDto payload);
}
