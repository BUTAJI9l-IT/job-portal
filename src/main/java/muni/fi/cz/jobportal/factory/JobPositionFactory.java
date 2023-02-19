package muni.fi.cz.jobportal.factory;

import static muni.fi.cz.jobportal.utils.AuthenticationUtils.getCurrentUser;

import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.api.detail.JobPositionDetailDto;
import muni.fi.cz.jobportal.domain.JobCategory;
import muni.fi.cz.jobportal.domain.JobPosition;
import muni.fi.cz.jobportal.repository.JobPositionRepository;
import org.mapstruct.ObjectFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobPositionFactory {

  private final JobPositionRepository jobPositionRepository;

  @ObjectFactory
  public JobPositionDetailDto prepare(JobPosition source) {
    final var detail = new JobPositionDetailDto();
    final var currentUser = getCurrentUser();
    detail.setApplied(jobPositionRepository.userWithIdApplied(source.getId(), currentUser));
    detail.setFavourite(jobPositionRepository.userWithIdLiked(source, currentUser));
    detail.setAppliedCount(source.getApplications().size());
    detail.setJobCategories(source.getJobCategories().stream().map(JobCategory::getName).toList());
    return detail;
  }
}
