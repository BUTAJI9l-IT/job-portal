package muni.fi.cz.jobportal.mapper;

import muni.fi.cz.jobportal.AbstractTest;
import muni.fi.cz.jobportal.api.detail.ApplicantDetailDto;
import muni.fi.cz.jobportal.api.detail.JobPositionDetailDto;
import muni.fi.cz.jobportal.api.request.ApplicationCreateDto;
import muni.fi.cz.jobportal.api.request.ApplicationUpdateDto;
import muni.fi.cz.jobportal.domain.Applicant;
import muni.fi.cz.jobportal.domain.Application;
import muni.fi.cz.jobportal.domain.JobPosition;
import muni.fi.cz.jobportal.enums.ApplicationState;
import muni.fi.cz.jobportal.factory.ApplicationFactory;
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
    assertEquals(ApplicationState.OPEN, result.getState());
    verify(applicationFactory).prepare(any(ApplicationCreateDto.class));
  }

  @Test
  void mapDetailTest() {
    final var request = loadResource("application_entity.json", Application.class);
    when(applicantMapper.map(any(Applicant.class))).thenReturn(new ApplicantDetailDto());
    when(jobPositionMapper.map(any(JobPosition.class))).thenReturn(new JobPositionDetailDto());

    final var result = applicationMapper.map(request);

    assertEquals(request.getId(), result.getId());
    assertEquals(request.getState(), result.getState());
    assertEquals(request.getDate(), result.getDate());
  }

  @Test
  void mapDtoTest() {
    final var request = loadResource("application_entity.json", Application.class);

    final var result = applicationMapper.mapDto(request);

    assertEquals(request.getId(), result.getId());
    assertEquals(request.getState(), result.getState());
    assertEquals(request.getDate(), result.getDate());
    assertEquals(request.getApplicant().getId(), result.getApplicant().getId());
    assertEquals(request.getApplicant().getUser().getFullName(), result.getApplicant().getName());
    assertEquals(request.getJobPosition().getId(), result.getJobPosition().getId());
    assertEquals(request.getJobPosition().getPositionName(), result.getJobPosition().getName());
  }

  @Test
  void updateTest() {
    final var entity = loadResource("application_entity.json", Application.class);
    final var request = loadResource("application_update_request.json", ApplicationUpdateDto.class);

    applicationMapper.update(entity, request);

    assertEquals(request.getState(), entity.getState());
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
