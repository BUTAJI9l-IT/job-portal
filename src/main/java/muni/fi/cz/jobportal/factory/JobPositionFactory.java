package muni.fi.cz.jobportal.factory;

import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.api.common.JobPositionDto;
import muni.fi.cz.jobportal.api.common.ReferenceDto;
import muni.fi.cz.jobportal.api.detail.JobPositionDetailDto;
import muni.fi.cz.jobportal.api.request.JobPositionCreateDto;
import muni.fi.cz.jobportal.domain.JobPosition;
import muni.fi.cz.jobportal.repository.CompanyRepository;
import muni.fi.cz.jobportal.repository.JobCategoryRepository;
import muni.fi.cz.jobportal.repository.JobPositionRepository;
import muni.fi.cz.jobportal.utils.AuthorityValidator;
import muni.fi.cz.jobportal.utils.StaticObjectFactory;
import org.mapstruct.ObjectFactory;
import org.springframework.stereotype.Component;

/**
 * Object factory for job positions
 *
 * @author Vitalii Bortsov
 */
@Component
@RequiredArgsConstructor
public class JobPositionFactory {

  private final JobPositionRepository jobPositionRepository;
  private final JobCategoryRepository jobCategoryRepository;
  private final CompanyRepository companyRepository;
  private final StaticObjectFactory staticObjectFactory;
  private final AuthorityValidator authorityValidator;

  @ObjectFactory
  public JobPositionDetailDto prepare(JobPosition source) {
    final var detail = new JobPositionDetailDto();
    setFields(source, detail);
    detail.setAppliedCount(jobPositionRepository.countApplied(source.getId()));
    return detail;
  }

  @ObjectFactory
  public JobPositionDto prepareDto(JobPosition source) {
    final var detail = new JobPositionDto();
    setFieldsDto(source, detail);
    return detail;
  }

  @ObjectFactory
  public JobPosition prepare(JobPositionCreateDto source) {
    final var jobPosition = new JobPosition();
    jobPosition.setCompany(companyRepository.getOneByIdOrThrowNotFound(source.getCompany()));
    jobPosition.setJobCategories(jobCategoryRepository.findAllById(source.getJobCategories()));
    final var now = staticObjectFactory.now();
    jobPosition.setCreated(now);
    jobPosition.setLastUpdated(now);
    return jobPosition;
  }

  private void setFields(JobPosition source, JobPositionDetailDto detail) {
    final var currentUser = authorityValidator.getCurrentUserFromHeader();
    detail.setApplied(jobPositionRepository.userWithIdApplied(source.getId(), currentUser));
    detail.setFavourite(jobPositionRepository.userWithIdLiked(source, currentUser));
    if (source.getJobCategories() != null) {
      detail.setJobCategories(
        source.getJobCategories().stream()
          .map(cat -> ReferenceDto.builder().id(cat.getId()).name(cat.getName()).build())
          .toList());
    } else {
      detail.setJobCategories(new ArrayList<>());
    }
  }

  private void setFieldsDto(JobPosition source, JobPositionDto detail) {
    final var currentUser = authorityValidator.getCurrentUserFromHeader();
    detail.setApplied(jobPositionRepository.userWithIdApplied(source.getId(), currentUser));
    detail.setFavourite(jobPositionRepository.userWithIdLiked(source, currentUser));
    if (source.getJobCategories() != null) {
      detail.setJobCategories(
        source.getJobCategories().stream()
          .map(cat -> ReferenceDto.builder().id(cat.getId()).name(cat.getName()).build())
          .toList());
    } else {
      detail.setJobCategories(new ArrayList<>());
    }
  }
}
