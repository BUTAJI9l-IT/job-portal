package com.github.butaji9l.jobportal.be.factory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.github.butaji9l.jobportal.be.AbstractTest;
import com.github.butaji9l.jobportal.be.api.request.ApplicantCreateDto;
import com.github.butaji9l.jobportal.be.domain.User;
import com.github.butaji9l.jobportal.be.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ApplicantFactoryTest extends AbstractTest {

  @Mock
  private UserRepository userRepository;
  @InjectMocks
  private ApplicantFactory applicantFactory;

  @Test
  void prepareApplicationTest() {
    final var request = loadResource("applicant_create_request.json", ApplicantCreateDto.class);

    when(userRepository.getOneByIdOrThrowNotFound(any())).thenReturn(new User());

    final var result = applicantFactory.prepare(request);
    assertThat(result.getUser()).isNotNull();
  }
}
