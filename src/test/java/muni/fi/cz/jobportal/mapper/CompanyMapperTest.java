package muni.fi.cz.jobportal.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import muni.fi.cz.jobportal.AbstractTest;
import muni.fi.cz.jobportal.api.request.CompanyCreateDto;
import muni.fi.cz.jobportal.api.request.CompanyUpdateDto;
import muni.fi.cz.jobportal.api.request.UserCreateDto;
import muni.fi.cz.jobportal.domain.Company;
import muni.fi.cz.jobportal.domain.User;
import muni.fi.cz.jobportal.factory.CompanyFactory;
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
    assertEquals(request.getCompanyName(), result.getCompanyName());
    assertEquals(request.getCompanyLink(), result.getCompanyLink());
    assertEquals(request.getDescription(), result.getDescription());
    assertEquals(request.getCompanySize(), result.getCompanySize());
  }

  @Test
  void mapDetailTest() {
    final var request = loadResource("company_entity.json", Company.class);

    final var result = companyMapper.map(request);

    assertEquals(request.getUser().getEmail(), result.getEmail());
    assertEquals(request.getUser().getId(), result.getUserId());
    assertEquals(request.getId(), result.getId());
    assertEquals(request.getCompanyName(), result.getCompanyName());
    assertEquals(request.getCompanyLink(), result.getCompanyLink());
    assertEquals(request.getCompanySize(), result.getCompanySize());
    assertEquals(request.getDescription(), result.getDescription());
  }

  @Test
  void mapDtoTest() {
    final var request = loadResource("company_entity.json", Company.class);

    final var result = companyMapper.mapDto(request);

    assertEquals(request.getUser().getId(), result.getUserId());
    assertEquals(request.getId(), result.getId());
    assertEquals(request.getCompanyName(), result.getCompanyName());
    assertEquals(request.getCompanySize(), result.getCompanySize());
  }

  @Test
  void updateTest() {
    final var entity = loadResource("company_entity.json", Company.class);
    final var request = loadResource("company_update_request.json", CompanyUpdateDto.class);

    companyMapper.update(entity, request);

    assertEquals(request.getCompanyName(), entity.getCompanyName());
    assertEquals(request.getCompanyLink(), entity.getCompanyLink());
    assertEquals(request.getDescription(), entity.getDescription());
    assertEquals(request.getCompanySize(), entity.getCompanySize());
    assertEquals(request.getName(), entity.getUser().getName());
    assertEquals(request.getLastName(), entity.getUser().getLastName());
  }

  @Test
  void mapRequestTest() {
    final var userCreateDto = loadResource("user_create_request.json", UserCreateDto.class);
    final var user = loadResource("user_entity.json", User.class);

    final var result = companyMapper.map(userCreateDto, user);

    assertEquals(userCreateDto.getCompanyName(), result.getCompanyName());
    assertEquals(userCreateDto.getCompanyLink(), result.getCompanyLink());
    assertEquals(userCreateDto.getCompanySize(), result.getCompanySize());
    assertEquals(user.getId(), result.getUser());
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
