package muni.fi.cz.jobportal.mapper;

import muni.fi.cz.jobportal.api.detail.JobPositionDetailDto;
import muni.fi.cz.jobportal.api.request.JobPositionCreateDto;
import muni.fi.cz.jobportal.api.request.JobPositionUpdateDto;
import muni.fi.cz.jobportal.domain.JobPosition;
import muni.fi.cz.jobportal.factory.JobPositionFactory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper for job positions
 *
 * @author Vitalii Bortsov
 */
@Mapper(uses = JobPositionFactory.class)
public interface JobPositionMapper {

  @Mapping(target = "company.name", source = "company.companyName")
  @Mapping(target = "jobCategories", ignore = true)
  @Mapping(target = "applied", ignore = true)
  @Mapping(target = "appliedCount", ignore = true)
  JobPositionDetailDto map(JobPosition source);

  @Mapping(target = "company", ignore = true)
  @Mapping(target = "jobCategories", ignore = true)
  @Mapping(target = "status", constant = "ACTIVE")
  JobPosition map(JobPositionCreateDto source);

  @Mapping(target = "jobCategories", ignore = true)
  JobPosition update(@MappingTarget JobPosition target, JobPositionUpdateDto source);
}
