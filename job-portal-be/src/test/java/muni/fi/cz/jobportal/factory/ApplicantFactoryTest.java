package muni.fi.cz.jobportal.factory;

import muni.fi.cz.jobportal.AbstractTest;
import muni.fi.cz.jobportal.api.request.ApplicantCreateDto;
import muni.fi.cz.jobportal.domain.User;
import muni.fi.cz.jobportal.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
