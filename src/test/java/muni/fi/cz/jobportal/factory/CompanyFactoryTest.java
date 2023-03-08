package muni.fi.cz.jobportal.factory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.UUID;
import muni.fi.cz.jobportal.api.request.CompanyCreateDto;
import muni.fi.cz.jobportal.domain.User;
import muni.fi.cz.jobportal.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CompanyFactoryTest {

  @Mock
  private UserRepository userRepository;
  @InjectMocks
  private CompanyFactory companyFactory;

  @Test
  void prepareCompanyTest() {
    final var request = new CompanyCreateDto();
    request.setUser(UUID.randomUUID());

    when(userRepository.getOneByIdOrThrowNotFound(any())).thenReturn(new User());

    final var result = companyFactory.prepare(request);
    assertThat(result.getUser()).isNotNull();
  }
}
