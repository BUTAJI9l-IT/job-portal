package com.github.butaji9l.jobportal.be.factory;

import static com.github.butaji9l.jobportal.be.testutils.EntityUtils.preparePositionEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.github.butaji9l.jobportal.be.AbstractTest;
import com.github.butaji9l.jobportal.be.api.request.JobPositionCreateDto;
import com.github.butaji9l.jobportal.be.domain.Company;
import com.github.butaji9l.jobportal.be.domain.JobCategory;
import com.github.butaji9l.jobportal.be.enums.PositionState;
import com.github.butaji9l.jobportal.be.repository.CompanyRepository;
import com.github.butaji9l.jobportal.be.repository.JobCategoryRepository;
import com.github.butaji9l.jobportal.be.repository.JobPositionRepository;
import com.github.butaji9l.jobportal.be.utils.AuthorityValidator;
import com.github.butaji9l.jobportal.be.utils.StaticObjectFactory;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
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
  @Mock
  private AuthorityValidator authorityValidator;
  @InjectMocks
  private JobPositionFactory jobPositionFactory;

  @Test
  void prepareDetailTest() {
    final var source = preparePositionEntity(new Company(), PositionState.ACTIVE);

    when(authorityValidator.getCurrentUserFromHeader()).thenReturn(UUID.randomUUID());
    when(jobPositionRepository.countApplied(any())).thenReturn(0);
    when(jobPositionRepository.userWithIdLiked(any(), any())).thenReturn(true);
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
    final var request = loadResource("jop_position_create_request.json",
      JobPositionCreateDto.class);

    when(companyRepository.getOneByIdOrThrowNotFound(any())).thenReturn(new Company());
    when(jobCategoryRepository.findAllById(any())).thenReturn(List.of(new JobCategory()));
    when(staticObjectFactory.now()).thenReturn(now);

    final var result = jobPositionFactory.prepare(request);

    assertThat(result.getCreated()).isNotNull().isEqualTo(result.getLastUpdated()).isEqualTo(now);
    assertThat(result.getJobCategories()).hasSize(1);
    assertThat(result.getCompany()).isNotNull();
  }
}
