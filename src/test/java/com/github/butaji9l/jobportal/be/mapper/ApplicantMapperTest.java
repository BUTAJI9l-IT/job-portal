package com.github.butaji9l.jobportal.be.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.butaji9l.jobportal.be.AbstractTest;
import com.github.butaji9l.jobportal.be.api.request.ApplicantCreateDto;
import com.github.butaji9l.jobportal.be.api.request.ApplicantUpdateDto;
import com.github.butaji9l.jobportal.be.domain.Applicant;
import com.github.butaji9l.jobportal.be.factory.ApplicantFactory;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    Assertions.assertEquals(request.getUser().getName(), result.getName());
    Assertions.assertEquals(request.getUser().getLastName(), result.getLastName());
    Assertions.assertEquals(request.getUser().getEmail(), result.getEmail());
    Assertions.assertEquals(request.getUser().getId(), result.getUserId());
    Assertions.assertEquals(request.getId(), result.getId());
    Assertions.assertEquals(request.getCountry(), result.getCountry());
    Assertions.assertEquals(request.getState(), result.getState());
    Assertions.assertEquals(request.getCity(), result.getCity());
    Assertions.assertEquals(request.getProfile(), result.getProfile());
    Assertions.assertEquals(request.getPhone(), result.getPhone());
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
