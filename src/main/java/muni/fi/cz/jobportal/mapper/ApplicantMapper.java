package muni.fi.cz.jobportal.mapper;

import muni.fi.cz.jobportal.api.common.ApplicantDto;
import muni.fi.cz.jobportal.api.detail.ApplicantDetailDto;
import muni.fi.cz.jobportal.api.request.ApplicantCreateDto;
import muni.fi.cz.jobportal.api.request.ApplicantUpdateDto;
import muni.fi.cz.jobportal.domain.Applicant;
import muni.fi.cz.jobportal.factory.ApplicantFactory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

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
