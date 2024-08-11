package com.github.butaji9l.jobportal.be.mapper;

import com.github.butaji9l.jobportal.be.api.common.ApplicationDto;
import com.github.butaji9l.jobportal.be.api.detail.ApplicationDetailDto;
import com.github.butaji9l.jobportal.be.api.request.ApplicationCreateDto;
import com.github.butaji9l.jobportal.be.api.request.ApplicationUpdateDto;
import com.github.butaji9l.jobportal.be.domain.Application;
import com.github.butaji9l.jobportal.be.factory.ApplicationFactory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper for applications
 *
 * @author Vitalii Bortsov
 */
@Mapper(uses = {ApplicationFactory.class, ApplicantMapper.class, JobPositionMapper.class})
public interface ApplicationMapper {

  @Mapping(target = "jobPosition", ignore = true)
  @Mapping(target = "applicant", ignore = true)
  @Mapping(target = "state", constant = "OPEN")
  Application map(ApplicationCreateDto source);

  @Mapping(target = "applicant.name", source = "applicant.user.fullName")
  @Mapping(target = "jobPosition.name", source = "jobPosition.positionName")
  ApplicationDto mapDto(Application source);

  ApplicationDetailDto map(Application source);

  Application update(@MappingTarget Application target, ApplicationUpdateDto source);

}
