package com.github.butaji9l.jobportal.be.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.butaji9l.jobportal.be.AbstractTest;
import com.github.butaji9l.jobportal.be.api.request.CompanyCreateDto;
import com.github.butaji9l.jobportal.be.api.request.CompanyUpdateDto;
import com.github.butaji9l.jobportal.be.api.request.UserCreateDto;
import com.github.butaji9l.jobportal.be.domain.Company;
import com.github.butaji9l.jobportal.be.domain.User;
import com.github.butaji9l.jobportal.be.factory.CompanyFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CompanyMapperTest extends AbstractTest {

  @Mock
  private CompanyFactory companyFactory;
  @Mock
  private JobPositionMapper jobPositionMapper;
  @InjectMocks
  private CompanyMapperImpl companyMapper;

  @Test
  void mapEntityTest() {
    final var request = loadResource("company_create_request.json", CompanyCreateDto.class);
    when(companyFactory.prepare(any(CompanyCreateDto.class))).thenReturn(new Company());
    final var result = companyMapper.map(request);
    verify(companyFactory).prepare(any(CompanyCreateDto.class));
    Assertions.assertEquals(request.getCompanyName(), result.getCompanyName());
    Assertions.assertEquals(request.getCompanyLink(), result.getCompanyLink());
    Assertions.assertEquals(request.getDescription(), result.getDescription());
    Assertions.assertEquals(request.getCompanySize(), result.getCompanySize());
  }

  @Test
  void mapDetailTest() {
    final var request = loadResource("company_entity.json", Company.class);

    final var result = companyMapper.map(request);

    Assertions.assertEquals(request.getUser().getEmail(), result.getEmail());
    Assertions.assertEquals(request.getUser().getId(), result.getUserId());
    Assertions.assertEquals(request.getId(), result.getId());
    Assertions.assertEquals(request.getCompanyName(), result.getCompanyName());
    Assertions.assertEquals(request.getCompanyLink(), result.getCompanyLink());
    Assertions.assertEquals(request.getCompanySize(), result.getCompanySize());
    Assertions.assertEquals(request.getDescription(), result.getDescription());
  }

  @Test
  void mapDtoTest() {
    final var request = loadResource("company_entity.json", Company.class);

    final var result = companyMapper.mapDto(request);

    Assertions.assertEquals(request.getUser().getId(), result.getUserId());
    Assertions.assertEquals(request.getId(), result.getId());
    Assertions.assertEquals(request.getCompanyName(), result.getCompanyName());
    Assertions.assertEquals(request.getCompanySize(), result.getCompanySize());
  }

  @Test
  void updateTest() {
    final var entity = loadResource("company_entity.json", Company.class);
    final var request = loadResource("company_update_request.json", CompanyUpdateDto.class);

    companyMapper.update(entity, request);

    assertEquals(request.getCompanyName(), entity.getCompanyName());
    assertEquals(request.getCompanyLink(), entity.getCompanyLink());
    assertEquals(request.getDescription(), entity.getDescription());
    Assertions.assertEquals(request.getCompanySize(), entity.getCompanySize());
    assertEquals(request.getName(), entity.getUser().getName());
    assertEquals(request.getLastName(), entity.getUser().getLastName());
  }

  @Test
  void mapRequestTest() {
    final var userCreateDto = loadResource("user_create_request.json", UserCreateDto.class);
    final var user = loadResource("user_entity.json", User.class);

    final var result = companyMapper.map(userCreateDto, user);

    Assertions.assertEquals(userCreateDto.getCompanyName(), result.getCompanyName());
    Assertions.assertEquals(userCreateDto.getCompanyLink(), result.getCompanyLink());
    Assertions.assertEquals(userCreateDto.getCompanySize(), result.getCompanySize());
    Assertions.assertEquals(user.getId(), result.getUser());
  }

  @Test
  void mapEntityNullTest() {
    assertNull(companyMapper.map((CompanyCreateDto) null));
  }

  @Test
  void mapDetailNullTest() {
    assertNull(companyMapper.map((Company) null));
  }

  @Test
  void mapDtoNullTest() {
    assertNull(companyMapper.mapDto(null));
  }

  @Test
  void mapRequestNullTest() {
    assertNull(companyMapper.map(null, null));
  }

}
