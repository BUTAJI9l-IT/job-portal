package muni.fi.cz.jobportal.factory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import muni.fi.cz.jobportal.api.common.CompanyDto;
import muni.fi.cz.jobportal.api.common.ExperienceDto;
import muni.fi.cz.jobportal.api.common.ReferenceDto;
import muni.fi.cz.jobportal.domain.Company;
import muni.fi.cz.jobportal.domain.JobCategory;
import muni.fi.cz.jobportal.repository.CompanyRepository;
import muni.fi.cz.jobportal.repository.JobCategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExperienceFactoryTest {

  @Mock
  private JobCategoryRepository jobCategoryRepository;
  @Mock
  private CompanyRepository companyRepository;
  @InjectMocks
  private ExperienceFactory experienceFactory;

  @Test
  void prepareExperienceTest() {
    final var request = new ExperienceDto();
    request.setCompany(new CompanyDto());
    request.getCompany().setId(UUID.randomUUID());
    request.setJobCategories(List.of(ReferenceDto.builder().id(UUID.randomUUID()).build()));

    when(companyRepository.getOneByIdOrThrowNotFound(any())).thenReturn(new Company());
    when(jobCategoryRepository.findAllById(any())).thenReturn(List.of(new JobCategory()));

    final var result = experienceFactory.prepare(request);
    assertThat(result.getCompany()).isNotNull();
    assertThat(result.getJobCategories()).isNotEmpty().hasSize(1);
  }
}
