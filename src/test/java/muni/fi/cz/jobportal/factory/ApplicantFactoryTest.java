package muni.fi.cz.jobportal.factory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.UUID;
import muni.fi.cz.jobportal.api.request.ApplicantCreateDto;
import muni.fi.cz.jobportal.domain.User;
import muni.fi.cz.jobportal.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ApplicantFactoryTest {

  @Mock
  private UserRepository userRepository;
  @InjectMocks
  private ApplicantFactory applicantFactory;

  @Test
  void prepareApplicationTest() {
    final var request = new ApplicantCreateDto();
    request.setUser(UUID.randomUUID());

    when(userRepository.getOneByIdOrThrowNotFound(any())).thenReturn(new User());

    final var result = applicantFactory.prepare(request);
    assertThat(result.getUser()).isNotNull();
  }
}
