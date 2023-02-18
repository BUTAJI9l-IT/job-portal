package muni.fi.cz.jobportal.mapper;

import muni.fi.cz.jobportal.api.common.ApplicationDto;
import muni.fi.cz.jobportal.domain.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ApplicationMapper {

  @Mapping(target = "applicant.name", source = "applicant.user.fullName")
  @Mapping(target = "jobPosition.name", source = "jobPosition.positionName")
  ApplicationDto map(Application source);

}
