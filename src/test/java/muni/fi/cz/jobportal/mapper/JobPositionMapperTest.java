package muni.fi.cz.jobportal.mapper;

import muni.fi.cz.jobportal.AbstractTest;
import muni.fi.cz.jobportal.api.detail.JobPositionDetailDto;
import muni.fi.cz.jobportal.api.request.JobPositionCreateDto;
import muni.fi.cz.jobportal.api.request.JobPositionUpdateDto;
import muni.fi.cz.jobportal.domain.JobPosition;
import muni.fi.cz.jobportal.enums.PositionState;
import muni.fi.cz.jobportal.factory.JobPositionFactory;
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
class JobPositionMapperTest extends AbstractTest {

  @Mock
  private JobPositionFactory jobPositionFactory;
  @InjectMocks
  private JobPositionMapperImpl jobPositionMapper;

  @Test
  void mapEntityTest() {
    final var request = loadResource("jop_position_create_request.json", JobPositionCreateDto.class);
    when(jobPositionFactory.prepare(any(JobPositionCreateDto.class))).thenReturn(new JobPosition());
    final var result = jobPositionMapper.map(request);
    verify(jobPositionFactory).prepare(any(JobPositionCreateDto.class));
    assertEquals(request.getPositionName(), result.getPositionName());
    assertEquals(request.getCountry(), result.getCountry());
    assertEquals(request.getState(), result.getState());
    assertEquals(request.getCity(), result.getCity());
    assertEquals(request.getContactEmail(), result.getContactEmail());
    assertEquals(request.getDetail(), result.getDetail());
    assertEquals(PositionState.ACTIVE, result.getStatus());
  }

  @Test
  void mapDetailTest() {
    final var request = loadResource("job_position_entity.json", JobPosition.class);
    when(jobPositionFactory.prepare(any(JobPosition.class))).thenReturn(new JobPositionDetailDto());

    final var result = jobPositionMapper.map(request);

    assertEquals(request.getCompany().getId(), result.getCompany().getId());
    assertEquals(request.getCompany().getCompanyName(), result.getCompany().getName());
    assertEquals(request.getId(), result.getId());
    assertEquals(request.getPositionName(), result.getPositionName());
    assertEquals(request.getStatus(), result.getStatus());
    assertEquals(request.getCountry(), result.getCountry());
    assertEquals(request.getState(), result.getState());
    assertEquals(request.getCity(), result.getCity());
    assertEquals(request.getContactEmail(), result.getContactEmail());
    assertEquals(request.getDetail(), result.getDetail());
    assertEquals(request.getCreated(), result.getCreated());
    assertEquals(request.getLastUpdated(), result.getLastUpdated());
  }

  @Test
  void updateTest() {
    final var entity = loadResource("job_position_entity.json", JobPosition.class);
    final var request = loadResource("job_position_update_request.json", JobPositionUpdateDto.class);

    jobPositionMapper.update(entity, request);

    assertEquals(request.getStatus(), entity.getStatus());
    assertEquals(request.getPositionName(), entity.getPositionName());
    assertEquals(request.getCountry(), entity.getCountry());
    assertEquals(request.getState(), entity.getState());
    assertEquals(request.getCity(), entity.getCity());
    assertEquals(request.getContactEmail(), entity.getContactEmail());
    assertEquals(request.getDetail(), entity.getDetail());
  }

  @Test
  void mapEntityNullTest() {
    assertNull(jobPositionMapper.map((JobPositionCreateDto) null));
  }

  @Test
  void mapDetailNullTest() {
    assertNull(jobPositionMapper.map((JobPosition) null));
  }
}
