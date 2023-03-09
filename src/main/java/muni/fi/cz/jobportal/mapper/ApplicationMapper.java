package muni.fi.cz.jobportal.mapper;

import muni.fi.cz.jobportal.api.common.ApplicationDto;
import muni.fi.cz.jobportal.api.detail.ApplicationDetailDto;
import muni.fi.cz.jobportal.api.request.ApplicationCreateDto;
import muni.fi.cz.jobportal.api.request.ApplicationUpdateDto;
import muni.fi.cz.jobportal.domain.Application;
import muni.fi.cz.jobportal.factory.ApplicationFactory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

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
