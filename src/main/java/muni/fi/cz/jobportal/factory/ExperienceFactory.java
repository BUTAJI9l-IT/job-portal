package muni.fi.cz.jobportal.factory;

import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.api.common.ExperienceDto;
import muni.fi.cz.jobportal.api.common.ReferenceDto;
import muni.fi.cz.jobportal.domain.Experience;
import muni.fi.cz.jobportal.repository.CompanyRepository;
import muni.fi.cz.jobportal.repository.JobCategoryRepository;
import org.mapstruct.ObjectFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExperienceFactory {

  private final JobCategoryRepository jobCategoryRepository;
  private final CompanyRepository companyRepository;

  @ObjectFactory
  public Experience prepare(ExperienceDto source) {
    final var experience = new Experience();
    if (source.getCompany().getId() != null) {
      experience.setCompany(companyRepository.getOneByIdOrThrowNotFound(source.getCompany().getId()));
    }
    experience.setJobCategories(
      jobCategoryRepository.findAllById(source.getJobCategories().stream().map(ReferenceDto::getId).toList()));
    return experience;
  }
}
