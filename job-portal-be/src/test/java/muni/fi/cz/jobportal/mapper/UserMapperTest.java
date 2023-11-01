package muni.fi.cz.jobportal.mapper;

import muni.fi.cz.jobportal.AbstractTest;
import muni.fi.cz.jobportal.api.common.RegistrationRequest;
import muni.fi.cz.jobportal.api.request.UserCreateDto;
import muni.fi.cz.jobportal.domain.User;
import muni.fi.cz.jobportal.factory.UserFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserMapperTest extends AbstractTest {

  @Mock
  private UserFactory userFactory;
  @InjectMocks
  private UserMapperImpl userMapper;


  @Test
  void mapEntityTest() {
    final var request = loadResource("user_create_request.json", UserCreateDto.class);
    when(userFactory.prepare(any(UserCreateDto.class))).thenReturn(new User());
    final var result = userMapper.map(request);
    verify(userFactory).prepare(any(UserCreateDto.class));
    assertEquals(request.getEmail(), result.getEmail());
    assertEquals(request.getName(), result.getName());
    assertEquals(request.getLastName(), result.getLastName());
    assertEquals(request.getScope(), result.getScope());
  }

  @Test
  void mapDtoTest() {
    final var request = loadResource("user_entity.json", User.class);

    final var result = userMapper.map(request);

    assertEquals(request.getId(), result.getId());
    assertEquals(request.getEmail(), result.getEmail());
    assertEquals(request.getName(), result.getName());
    assertEquals(request.getLastName(), result.getLastName());
    assertEquals(request.getScope(), result.getScope());
  }

  @Test
  void mapRequestTest() {
    final var request = loadResource("registration_request.json", RegistrationRequest.class);

    final var result = userMapper.map(request);

    assertEquals(request.getScope(), result.getScope());
    assertEquals(request.getEmail(), result.getEmail());
    assertEquals(request.getName(), result.getName());
    assertEquals(request.getLastName(), result.getLastName());
    assertEquals(request.getPassword(), result.getPassword());
    assertEquals(request.getCompanyName(), result.getCompanyName());
    assertEquals(request.getCompanyLink(), result.getCompanyLink());
    assertEquals(request.getCompanySize(), result.getCompanySize());
  }

  @Test
  void mapEntityNullTest() {
    assertNull(userMapper.map((UserCreateDto) null));
  }

  @Test
  void mapDtoNullTest() {
    assertNull(userMapper.map((User) null));
  }

  @Test
  void mapRequestNullTest() {
    assertNull(userMapper.map((RegistrationRequest) null));
  }

}
