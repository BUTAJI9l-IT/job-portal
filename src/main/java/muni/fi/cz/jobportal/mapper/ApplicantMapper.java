package muni.fi.cz.jobportal.mapper;

import muni.fi.cz.jobportal.api.detail.ApplicantDetailDto;
import muni.fi.cz.jobportal.api.request.ApplicantCreateDto;
import muni.fi.cz.jobportal.domain.Applicant;
import muni.fi.cz.jobportal.factory.ApplicantFactory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = ApplicantFactory.class)
public interface ApplicantMapper {

  @Mapping(target = "user", ignore = true)
  Applicant map(ApplicantCreateDto request);

  @Mapping(target = "name", source = "user.name")
  @Mapping(target = "lastName", source = "user.lastName")
  @Mapping(target = "email", source = "user.email")
  ApplicantDetailDto map(Applicant source);

}
