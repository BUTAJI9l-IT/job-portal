package muni.fi.cz.jobportal.factory;

import static muni.fi.cz.jobportal.testutils.EntityUtils.preparePositionEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;
import muni.fi.cz.jobportal.AbstractTest;
import muni.fi.cz.jobportal.api.request.JobPositionCreateDto;
import muni.fi.cz.jobportal.domain.Company;
import muni.fi.cz.jobportal.domain.JobCategory;
import muni.fi.cz.jobportal.enums.PositionState;
import muni.fi.cz.jobportal.repository.CompanyRepository;
import muni.fi.cz.jobportal.repository.JobCategoryRepository;
import muni.fi.cz.jobportal.repository.JobPositionRepository;
import muni.fi.cz.jobportal.utils.StaticObjectFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JobPositionFactoryTest extends AbstractTest {

  @Mock
  private JobPositionRepository jobPositionRepository;
  @Mock
  private JobCategoryRepository jobCategoryRepository;
  @Mock
  private CompanyRepository companyRepository;
  @Mock
  private StaticObjectFactory staticObjectFactory;
  @InjectMocks
  private JobPositionFactory jobPositionFactory;

  @Test
  void prepareDetailTest() {
    final var source = preparePositionEntity(new Company(), PositionState.ACTIVE);

    when(jobPositionRepository.countApplied(any())).thenReturn(0);
    when(jobPositionRepository.applicantWithIdLiked(any(), any())).thenReturn(true);
    when(jobPositionRepository.userWithIdApplied(any(), any())).thenReturn(true);

    final var result = jobPositionFactory.prepare(source);

    assertThat(result.getAppliedCount()).isZero();
    assertThat(result.getApplied()).isTrue();
    assertThat(result.getFavourite()).isTrue();
    assertThat(result.getJobCategories()).isNotNull();
  }

  @Test
  void prepareJobPositionTest() {
    final var now = Instant.now();
    final var request = loadResource("jop_position_create_request.json", JobPositionCreateDto.class);

    when(companyRepository.getOneByIdOrThrowNotFound(any())).thenReturn(new Company());
    when(jobCategoryRepository.findAllById(any())).thenReturn(List.of(new JobCategory()));
    when(staticObjectFactory.now()).thenReturn(now);

    final var result = jobPositionFactory.prepare(request);

    assertThat(result.getCreated()).isNotNull().isEqualTo(result.getLastUpdated()).isEqualTo(now);
    assertThat(result.getJobCategories()).hasSize(1);
    assertThat(result.getCompany()).isNotNull();
  }
}
