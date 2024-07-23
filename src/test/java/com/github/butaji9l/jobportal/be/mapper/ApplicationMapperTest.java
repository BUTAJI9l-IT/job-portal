package com.github.butaji9l.jobportal.be.mapper;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.butaji9l.jobportal.be.AbstractTest;
import com.github.butaji9l.jobportal.be.api.detail.ApplicantDetailDto;
import com.github.butaji9l.jobportal.be.api.detail.JobPositionDetailDto;
import com.github.butaji9l.jobportal.be.api.request.ApplicationCreateDto;
import com.github.butaji9l.jobportal.be.api.request.ApplicationUpdateDto;
import com.github.butaji9l.jobportal.be.domain.Applicant;
import com.github.butaji9l.jobportal.be.domain.Application;
import com.github.butaji9l.jobportal.be.domain.JobPosition;
import com.github.butaji9l.jobportal.be.enums.ApplicationState;
import com.github.butaji9l.jobportal.be.factory.ApplicationFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ApplicationMapperTest extends AbstractTest {

  @Mock
  private ApplicationFactory applicationFactory;
  @Mock
  private ApplicantMapper applicantMapper;
  @Mock
  private JobPositionMapper jobPositionMapper;
  @InjectMocks
  private ApplicationMapperImpl applicationMapper;

  @Test
  void mapEntityTest() {
    when(applicationFactory.prepare(any(ApplicationCreateDto.class))).thenReturn(new Application());
    final var result = applicationMapper.map(new ApplicationCreateDto());
    Assertions.assertEquals(ApplicationState.OPEN, result.getState());
    verify(applicationFactory).prepare(any(ApplicationCreateDto.class));
  }

  @Test
  void mapDetailTest() {
    final var request = loadResource("application_entity.json", Application.class);
    when(applicantMapper.map(any(Applicant.class))).thenReturn(new ApplicantDetailDto());
    when(jobPositionMapper.map(any(JobPosition.class))).thenReturn(new JobPositionDetailDto());

    final var result = applicationMapper.map(request);

    Assertions.assertEquals(request.getId(), result.getId());
    Assertions.assertEquals(request.getState(), result.getState());
    Assertions.assertEquals(request.getDate(), result.getDate());
  }

  @Test
  void mapDtoTest() {
    final var request = loadResource("application_entity.json", Application.class);

    final var result = applicationMapper.mapDto(request);

    Assertions.assertEquals(request.getId(), result.getId());
    Assertions.assertEquals(request.getState(), result.getState());
    Assertions.assertEquals(request.getDate(), result.getDate());
    Assertions.assertEquals(request.getApplicant().getId(), result.getApplicant().getId());
    Assertions.assertEquals(request.getApplicant().getUser().getFullName(),
      result.getApplicant().getName());
    Assertions.assertEquals(request.getJobPosition().getId(), result.getJobPosition().getId());
    Assertions.assertEquals(request.getJobPosition().getPositionName(),
      result.getJobPosition().getName());
  }

  @Test
  void updateTest() {
    final var entity = loadResource("application_entity.json", Application.class);
    final var request = loadResource("application_update_request.json", ApplicationUpdateDto.class);

    applicationMapper.update(entity, request);

    Assertions.assertEquals(request.getState(), entity.getState());
  }

  @Test
  void mapEntityNullTest() {
    assertNull(applicationMapper.map((ApplicationCreateDto) null));
  }

  @Test
  void mapDetailNullTest() {
    assertNull(applicationMapper.map((Application) null));
  }

  @Test
  void mapDtoNullTest() {
    assertNull(applicationMapper.mapDto(null));
  }

}
