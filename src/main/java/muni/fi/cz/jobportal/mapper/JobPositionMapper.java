package muni.fi.cz.jobportal.mapper;

import muni.fi.cz.jobportal.api.detail.JobPositionDetailDto;
import muni.fi.cz.jobportal.domain.JobPosition;
import muni.fi.cz.jobportal.factory.JobPositionFactory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = JobPositionFactory.class)
public interface JobPositionMapper {

  @Mapping(target = "company.name", source = "company.companyName")
  @Mapping(target = "jobCategories", ignore = true)
  @Mapping(target = "applied", ignore = true)
  @Mapping(target = "appliedCount", ignore = true)
  JobPositionDetailDto map(JobPosition source);
}
