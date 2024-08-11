package com.github.butaji9l.jobportal.be.factory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.github.butaji9l.jobportal.be.AbstractTest;
import com.github.butaji9l.jobportal.be.api.common.CompanyDto;
import com.github.butaji9l.jobportal.be.api.common.ExperienceDto;
import com.github.butaji9l.jobportal.be.domain.Company;
import com.github.butaji9l.jobportal.be.domain.Experience;
import com.github.butaji9l.jobportal.be.domain.JobCategory;
import com.github.butaji9l.jobportal.be.mapper.CompanyMapper;
import com.github.butaji9l.jobportal.be.repository.CompanyRepository;
import com.github.butaji9l.jobportal.be.repository.JobCategoryRepository;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExperienceFactoryTest extends AbstractTest {

  @Mock
  private JobCategoryRepository jobCategoryRepository;
  @Mock
  private CompanyRepository companyRepository;
  @Mock
  private CompanyMapper companyMapper;
  @InjectMocks
  private ExperienceFactory experienceFactory;

  @Test
  void prepareExperienceTest() {
    final var request = loadResource("experience_dto.json", ExperienceDto.class);

    when(companyRepository.getOneByIdOrThrowNotFound(any())).thenReturn(new Company());
    when(jobCategoryRepository.findAllById(any())).thenReturn(List.of(new JobCategory()));

    final var result = experienceFactory.prepare(request);
    Assertions.assertThat(result.getCompany()).isNotNull();
    Assertions.assertThat(result.getJobCategories()).isNotEmpty().hasSize(1);
  }

  @Test
  void prepareExperienceDtoTest_withCompany() {
    final var request = loadResource("experience_entity.json", Experience.class);

    when(companyMapper.mapDto(any())).thenAnswer(i -> {
      final var result = new CompanyDto();
      result.setId(UUID.randomUUID());
      return result;
    });

    final var result = experienceFactory.prepare(request);
    Assertions.assertThat(result.getCompany()).isNotNull();
    Assertions.assertThat(result.getCompany().getId()).isNotNull();
  }

  @Test
  void prepareExperienceDtoTest_withoutCompany() {
    final var request = loadResource("experience_entity.json", Experience.class);
    request.setCompany(null);
    final var result = experienceFactory.prepare(request);
    Assertions.assertThat(result.getCompany()).isNotNull();
    Assertions.assertThat(result.getCompany().getId()).isNull();
    Assertions.assertThat(result.getCompany().getCompanyName()).isNotEmpty();
  }
}
