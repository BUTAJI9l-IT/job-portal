package muni.fi.cz.jobportal.factory;

import static muni.fi.cz.jobportal.utils.AuthenticationUtils.getCurrentUser;

import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.api.common.JobPositionDto;
import muni.fi.cz.jobportal.api.detail.JobPositionDetailDto;
import muni.fi.cz.jobportal.api.request.JobPositionCreateDto;
import muni.fi.cz.jobportal.domain.JobCategory;
import muni.fi.cz.jobportal.domain.JobPosition;
import muni.fi.cz.jobportal.repository.CompanyRepository;
import muni.fi.cz.jobportal.repository.JobCategoryRepository;
import muni.fi.cz.jobportal.repository.JobPositionRepository;
import muni.fi.cz.jobportal.utils.StaticObjectFactory;
import org.mapstruct.ObjectFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobPositionFactory {

  private final JobPositionRepository jobPositionRepository;
  private final JobCategoryRepository jobCategoryRepository;
  private final CompanyRepository companyRepository;
  private final StaticObjectFactory staticObjectFactory;

  @ObjectFactory
  public JobPositionDetailDto prepare(JobPosition source) {
    final var detail = new JobPositionDetailDto();
    setFields(source, detail);
    detail.setAppliedCount(jobPositionRepository.countApplied(source.getId()));
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

  private void setFields(JobPosition source, JobPositionDto detail) {
    final var currentUser = getCurrentUser();
    detail.setApplied(jobPositionRepository.userWithIdApplied(source.getId(), currentUser));
    detail.setFavourite(jobPositionRepository.userWithIdLiked(source, currentUser));
    if (source.getJobCategories() != null) {
      detail.setJobCategories(source.getJobCategories().stream().map(JobCategory::getName).toList());
    } else {
      detail.setJobCategories(new ArrayList<>());
    }
  }
}
