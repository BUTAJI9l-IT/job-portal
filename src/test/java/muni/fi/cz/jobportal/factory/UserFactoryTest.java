package muni.fi.cz.jobportal.factory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import muni.fi.cz.jobportal.api.common.RepeatPasswordDto;
import muni.fi.cz.jobportal.api.request.UserCreateDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserFactoryTest {

  @Mock
  private PasswordEncoder encoder;
  @InjectMocks
  private UserFactory userFactory;
  @Captor
  private ArgumentCaptor<String> stringCaptor;

  @Test
  void prepareUserTest() {
    final var request = new UserCreateDto();
    final var repeatPasswordDto = new RepeatPasswordDto();
    repeatPasswordDto.setPassword("qwerty");
    repeatPasswordDto.setRepeatPassword("qwerty");
    request.setPassword(repeatPasswordDto);

    when(encoder.encode(anyString())).thenReturn("encoded");

    assertThat(userFactory.prepare(request).getPassword()).isEqualTo("encoded");
    verify(encoder).encode(stringCaptor.capture());
    assertThat(stringCaptor.getValue()).isEqualTo("qwerty");
  }
}
