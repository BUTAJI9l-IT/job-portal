package com.github.butaji9l.jobportal.be.factory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.github.butaji9l.jobportal.be.AbstractTest;
import com.github.butaji9l.jobportal.be.api.request.ApplicationCreateDto;
import com.github.butaji9l.jobportal.be.domain.Applicant;
import com.github.butaji9l.jobportal.be.domain.JobPosition;
import com.github.butaji9l.jobportal.be.repository.ApplicantRepository;
import com.github.butaji9l.jobportal.be.repository.JobPositionRepository;
import com.github.butaji9l.jobportal.be.utils.StaticObjectFactory;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ApplicationFactoryTest extends AbstractTest {

  @Mock
  private StaticObjectFactory staticObjectFactory;
  @Mock
  private JobPositionRepository jobPositionRepository;
  @Mock
  private ApplicantRepository applicantRepository;
  @InjectMocks
  private ApplicationFactory applicationFactory;

  @Test
  void prepareApplicationTest() {
    final var request = loadResource("application_create_request.json", ApplicationCreateDto.class);

    when(applicantRepository.getOneByIdOrThrowNotFound(any())).thenReturn(new Applicant());
    when(jobPositionRepository.getOneByIdOrThrowNotFound(any())).thenReturn(new JobPosition());
    when(staticObjectFactory.now()).thenReturn(Instant.now());

    final var result = applicationFactory.prepare(request);

    assertThat(result.getJobPosition()).isNotNull();
    assertThat(result.getApplicant()).isNotNull();
    assertThat(result.getDate()).isNotNull();
  }

}
