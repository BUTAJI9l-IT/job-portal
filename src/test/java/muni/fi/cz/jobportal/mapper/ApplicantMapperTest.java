package muni.fi.cz.jobportal.mapper;

import muni.fi.cz.jobportal.AbstractTest;
import muni.fi.cz.jobportal.api.request.ApplicantCreateDto;
import muni.fi.cz.jobportal.api.request.ApplicantUpdateDto;
import muni.fi.cz.jobportal.domain.Applicant;
import muni.fi.cz.jobportal.factory.ApplicantFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicantMapperTest extends AbstractTest {

  @Mock
  private ApplicantFactory applicantFactory;
  @Mock
  private ExperienceMapper experienceMapper;
  @InjectMocks
  private ApplicantMapperImpl applicantMapper;

  @Test
  void mapEntityTest() {
    when(applicantFactory.prepare(any(ApplicantCreateDto.class))).thenReturn(new Applicant());
    applicantMapper.map(new ApplicantCreateDto());
    verify(applicantFactory).prepare(any(ApplicantCreateDto.class));
  }

  @Test
  void mapDetailTest() {
    final var request = loadResource("applicant_entity.json", Applicant.class);
    when(experienceMapper.map(anyList())).thenReturn(new ArrayList<>());

    final var result = applicantMapper.map(request);

    assertEquals(request.getUser().getName(), result.getName());
    assertEquals(request.getUser().getLastName(), result.getLastName());
    assertEquals(request.getUser().getEmail(), result.getEmail());
    assertEquals(request.getUser().getId(), result.getUserId());
    assertEquals(request.getId(), result.getId());
    assertEquals(request.getCountry(), result.getCountry());
    assertEquals(request.getState(), result.getState());
    assertEquals(request.getCity(), result.getCity());
    assertEquals(request.getProfile(), result.getProfile());
    assertEquals(request.getPhone(), result.getPhone());
  }

  @Test
  void mapDtoTest() {
    final var request = loadResource("applicant_entity.json", Applicant.class);

    final var result = applicantMapper.mapDto(request);

    assertEquals(request.getUser().getName(), result.getName());
    assertEquals(request.getUser().getLastName(), result.getLastName());
    assertEquals(request.getUser().getEmail(), result.getEmail());
    assertEquals(request.getUser().getId(), result.getUserId());
    assertEquals(request.getId(), result.getId());
  }

  @Test
  void updateTest() {
    final var entity = loadResource("applicant_entity.json", Applicant.class);
    final var request = loadResource("applicant_update_request.json", ApplicantUpdateDto.class);

    applicantMapper.update(entity, request);

    assertEquals(request.getCountry(), entity.getCountry());
    assertEquals(request.getState(), entity.getState());
    assertEquals(request.getCity(), entity.getCity());
    assertEquals(request.getPhone(), entity.getPhone());
    assertEquals(request.getProfile(), entity.getProfile());
    assertEquals(request.getName(), entity.getUser().getName());
    assertEquals(request.getLastName(), entity.getUser().getLastName());
  }

  @Test
  void mapEntityNullTest() {
    assertNull(applicantMapper.map((ApplicantCreateDto) null));
  }

  @Test
  void mapDetailNullTest() {
    assertNull(applicantMapper.map((Applicant) null));
  }

  @Test
  void mapDtoNullTest() {
    assertNull(applicantMapper.mapDto(null));
  }

}
