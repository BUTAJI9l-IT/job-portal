package com.github.butaji9l.jobportal.be.mapper;

import com.github.butaji9l.jobportal.be.api.common.JobPositionDto;
import com.github.butaji9l.jobportal.be.api.detail.JobPositionDetailDto;
import com.github.butaji9l.jobportal.be.api.request.JobPositionCreateDto;
import com.github.butaji9l.jobportal.be.api.request.JobPositionUpdateDto;
import com.github.butaji9l.jobportal.be.domain.JobPosition;
import com.github.butaji9l.jobportal.be.factory.JobPositionFactory;
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
  @Mapping(target = "company.userId", source = "company.user.id")
  @Mapping(target = "jobCategories", ignore = true)
  @Mapping(target = "applied", ignore = true)
  @Mapping(target = "appliedCount", ignore = true)
  JobPositionDetailDto map(JobPosition source);

  @Mapping(target = "company.name", source = "company.companyName")
  @Mapping(target = "company.userId", source = "company.user.id")
  @Mapping(target = "jobCategories", ignore = true)
  @Mapping(target = "applied", ignore = true)
  JobPositionDto mapDto(JobPosition source);

  @Mapping(target = "company", ignore = true)
  @Mapping(target = "jobCategories", ignore = true)
  @Mapping(target = "status", constant = "ACTIVE")
  JobPosition map(JobPositionCreateDto source);

  @Mapping(target = "jobCategories", ignore = true)
  JobPosition update(@MappingTarget JobPosition target, JobPositionUpdateDto source);
}
