package com.github.butaji9l.jobportal.be.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.butaji9l.jobportal.be.AbstractTest;
import com.github.butaji9l.jobportal.be.api.detail.JobPositionDetailDto;
import com.github.butaji9l.jobportal.be.api.request.JobPositionCreateDto;
import com.github.butaji9l.jobportal.be.api.request.JobPositionUpdateDto;
import com.github.butaji9l.jobportal.be.domain.JobPosition;
import com.github.butaji9l.jobportal.be.enums.PositionState;
import com.github.butaji9l.jobportal.be.factory.JobPositionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JobPositionMapperTest extends AbstractTest {

  @Mock
  private JobPositionFactory jobPositionFactory;
  @InjectMocks
  private JobPositionMapperImpl jobPositionMapper;

  @Test
  void mapEntityTest() {
    final var request = loadResource("jop_position_create_request.json",
      JobPositionCreateDto.class);
    when(jobPositionFactory.prepare(any(JobPositionCreateDto.class))).thenReturn(new JobPosition());
    final var result = jobPositionMapper.map(request);
    verify(jobPositionFactory).prepare(any(JobPositionCreateDto.class));
    Assertions.assertEquals(request.getPositionName(), result.getPositionName());
    Assertions.assertEquals(request.getCountry(), result.getCountry());
    Assertions.assertEquals(request.getState(), result.getState());
    Assertions.assertEquals(request.getCity(), result.getCity());
    Assertions.assertEquals(request.getContactEmail(), result.getContactEmail());
    Assertions.assertEquals(request.getDetail(), result.getDetail());
    Assertions.assertEquals(PositionState.ACTIVE, result.getStatus());
  }

  @Test
  void mapDetailTest() {
    final var request = loadResource("job_position_entity.json", JobPosition.class);
    when(jobPositionFactory.prepare(any(JobPosition.class))).thenReturn(new JobPositionDetailDto());

    final var result = jobPositionMapper.map(request);

    Assertions.assertEquals(request.getCompany().getId(), result.getCompany().getId());
    Assertions.assertEquals(request.getCompany().getCompanyName(), result.getCompany().getName());
    Assertions.assertEquals(request.getId(), result.getId());
    Assertions.assertEquals(request.getPositionName(), result.getPositionName());
    Assertions.assertEquals(request.getStatus(), result.getStatus());
    Assertions.assertEquals(request.getCountry(), result.getCountry());
    Assertions.assertEquals(request.getState(), result.getState());
    Assertions.assertEquals(request.getCity(), result.getCity());
    Assertions.assertEquals(request.getContactEmail(), result.getContactEmail());
    Assertions.assertEquals(request.getDetail(), result.getDetail());
    Assertions.assertEquals(request.getCreated(), result.getCreated());
    Assertions.assertEquals(request.getLastUpdated(), result.getLastUpdated());
  }

  @Test
  void updateTest() {
    final var entity = loadResource("job_position_entity.json", JobPosition.class);
    final var request = loadResource("job_position_update_request.json",
      JobPositionUpdateDto.class);

    jobPositionMapper.update(entity, request);

    Assertions.assertEquals(request.getStatus(), entity.getStatus());
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
