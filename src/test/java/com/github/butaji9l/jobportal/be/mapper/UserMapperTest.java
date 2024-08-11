package com.github.butaji9l.jobportal.be.mapper;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.butaji9l.jobportal.be.AbstractTest;
import com.github.butaji9l.jobportal.be.api.common.RegistrationRequest;
import com.github.butaji9l.jobportal.be.api.request.UserCreateDto;
import com.github.butaji9l.jobportal.be.domain.User;
import com.github.butaji9l.jobportal.be.factory.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    Assertions.assertEquals(request.getEmail(), result.getEmail());
    Assertions.assertEquals(request.getName(), result.getName());
    Assertions.assertEquals(request.getLastName(), result.getLastName());
    Assertions.assertEquals(request.getScope(), result.getScope());
  }

  @Test
  void mapDtoTest() {
    final var request = loadResource("user_entity.json", User.class);

    final var result = userMapper.map(request);

    Assertions.assertEquals(request.getId(), result.getId());
    Assertions.assertEquals(request.getEmail(), result.getEmail());
    Assertions.assertEquals(request.getName(), result.getName());
    Assertions.assertEquals(request.getLastName(), result.getLastName());
    Assertions.assertEquals(request.getScope(), result.getScope());
  }

  @Test
  void mapRequestTest() {
    final var request = loadResource("registration_request.json", RegistrationRequest.class);

    final var result = userMapper.map(request);

    Assertions.assertEquals(request.getScope(), result.getScope());
    Assertions.assertEquals(request.getEmail(), result.getEmail());
    Assertions.assertEquals(request.getName(), result.getName());
    Assertions.assertEquals(request.getLastName(), result.getLastName());
    Assertions.assertEquals(request.getPassword(), result.getPassword());
    Assertions.assertEquals(request.getCompanyName(), result.getCompanyName());
    Assertions.assertEquals(request.getCompanyLink(), result.getCompanyLink());
    Assertions.assertEquals(request.getCompanySize(), result.getCompanySize());
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
