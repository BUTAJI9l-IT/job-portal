package com.github.butaji9l.jobportal.be.factory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.butaji9l.jobportal.be.AbstractTest;
import com.github.butaji9l.jobportal.be.api.request.UserCreateDto;
import com.github.butaji9l.jobportal.be.domain.UserPreferences;
import com.github.butaji9l.jobportal.be.mapper.PreferencesMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserFactoryTest extends AbstractTest {

  @Mock
  private PasswordEncoder encoder;
  @Mock

  private PreferencesMapper preferencesMapper;
  @InjectMocks
  private UserFactory userFactory;
  @Captor
  private ArgumentCaptor<String> stringCaptor;

  @Test
  void prepareUserTest() {
    final var request = loadResource("user_create_request.json", UserCreateDto.class);

    when(encoder.encode(anyString())).thenReturn("encoded");
    when(preferencesMapper.mapPreferences(any(UserCreateDto.class))).thenReturn(
      new UserPreferences());

    assertThat(userFactory.prepare(request).getPassword()).isEqualTo("encoded");
    verify(encoder).encode(stringCaptor.capture());
    assertThat(stringCaptor.getValue()).isEqualTo(request.getPassword().getPassword());
  }
}
