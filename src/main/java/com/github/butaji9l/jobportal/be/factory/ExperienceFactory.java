package com.github.butaji9l.jobportal.be.factory;

import com.github.butaji9l.jobportal.be.api.common.CompanyDto;
import com.github.butaji9l.jobportal.be.api.common.ExperienceDto;
import com.github.butaji9l.jobportal.be.api.common.ReferenceDto;
import com.github.butaji9l.jobportal.be.domain.Experience;
import com.github.butaji9l.jobportal.be.mapper.CompanyMapper;
import com.github.butaji9l.jobportal.be.repository.CompanyRepository;
import com.github.butaji9l.jobportal.be.repository.JobCategoryRepository;
import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.mapstruct.ObjectFactory;
import org.springframework.stereotype.Component;

/**
 * Object factory for experiences
 *
 * @author Vitalii Bortsov
 */
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
      experience.setCompany(
        companyRepository.getOneByIdOrThrowNotFound(source.getCompany().getId()));
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
