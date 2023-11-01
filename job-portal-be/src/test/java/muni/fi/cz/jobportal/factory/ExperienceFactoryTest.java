package muni.fi.cz.jobportal.factory;

import muni.fi.cz.jobportal.AbstractTest;
import muni.fi.cz.jobportal.api.common.CompanyDto;
import muni.fi.cz.jobportal.api.common.ExperienceDto;
import muni.fi.cz.jobportal.domain.Company;
import muni.fi.cz.jobportal.domain.Experience;
import muni.fi.cz.jobportal.domain.JobCategory;
import muni.fi.cz.jobportal.mapper.CompanyMapper;
import muni.fi.cz.jobportal.repository.CompanyRepository;
import muni.fi.cz.jobportal.repository.JobCategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
    assertThat(result.getCompany()).isNotNull();
    assertThat(result.getJobCategories()).isNotEmpty().hasSize(1);
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
    assertThat(result.getCompany()).isNotNull();
    assertThat(result.getCompany().getId()).isNotNull();
  }

  @Test
  void prepareExperienceDtoTest_withoutCompany() {
    final var request = loadResource("experience_entity.json", Experience.class);
    request.setCompany(null);
    final var result = experienceFactory.prepare(request);
    assertThat(result.getCompany()).isNotNull();
    assertThat(result.getCompany().getId()).isNull();
    assertThat(result.getCompany().getCompanyName()).isNotEmpty();
  }
}
