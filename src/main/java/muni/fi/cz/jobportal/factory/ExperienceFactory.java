package muni.fi.cz.jobportal.factory;

import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.api.common.CompanyDto;
import muni.fi.cz.jobportal.api.common.ExperienceDto;
import muni.fi.cz.jobportal.api.common.ReferenceDto;
import muni.fi.cz.jobportal.domain.Experience;
import muni.fi.cz.jobportal.mapper.CompanyMapper;
import muni.fi.cz.jobportal.repository.CompanyRepository;
import muni.fi.cz.jobportal.repository.JobCategoryRepository;
import org.mapstruct.ObjectFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExperienceFactory {

  private final JobCategoryRepository jobCategoryRepository;
  private final CompanyMapper companyMapper;
  private final CompanyRepository companyRepository;

  @ObjectFactory
  public Experience prepare(ExperienceDto source) {
    final var experience = new Experience();
    if (source.getCompany().getId() != null) {
      experience.setCompany(companyRepository.getOneByIdOrThrowNotFound(source.getCompany().getId()));
    }
    experience.setJobCategories(
        jobCategoryRepository.findAllById(
            Optional.ofNullable(source.getJobCategories())
                .map(u -> u.stream().map(ReferenceDto::getId).toList())
                .orElse(new ArrayList<>())));
    return experience;
  }

  @ObjectFactory
  public ExperienceDto prepare(Experience source) {
    final var experienceDto = new ExperienceDto();
    if (source.getCompany() != null) {
      experienceDto.setCompany(companyMapper.mapDto(source.getCompany()));
    } else {
      experienceDto.setCompany(new CompanyDto());
      experienceDto.getCompany().setCompanyName(source.getCompanyName());
    }
    return experienceDto;
  }
}
