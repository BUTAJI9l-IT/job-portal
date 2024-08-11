package com.github.butaji9l.jobportal.be.mapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.github.butaji9l.jobportal.be.AbstractTest;
import com.github.butaji9l.jobportal.be.api.common.ExperienceDto;
import com.github.butaji9l.jobportal.be.domain.Applicant;
import com.github.butaji9l.jobportal.be.domain.Experience;
import com.github.butaji9l.jobportal.be.factory.ExperienceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExperienceMapperTest extends AbstractTest {

  @Mock
  private ExperienceFactory experienceFactory;
  @InjectMocks
  private ExperienceMapperImpl experienceMapper;

  @Test
  void mapDtoTest() {
    final var request = loadResource("experience_entity.json", Experience.class);
    when(experienceFactory.prepare(any(Experience.class))).thenReturn(new ExperienceDto());

    final var result = experienceMapper.map(request);

    Assertions.assertEquals(request.getFromDate(), result.getDateRange().getFromDate());
    Assertions.assertEquals(request.getToDate(), result.getDateRange().getToDate());
    Assertions.assertEquals(request.getId(), result.getId());
    Assertions.assertEquals(request.getOccupation(), result.getOccupation());
    Assertions.assertEquals(request.getJobCategories().size(), result.getJobCategories().size());
  }

  @Test
  void createTest() {
    final var request = loadResource("applicant_entity.json", Applicant.class);
    final var requestExp = loadResource("experience_dto.json", ExperienceDto.class);

    when(experienceFactory.prepare(any(ExperienceDto.class))).thenReturn(new Experience());

    final var result = experienceMapper.create(requestExp, request);

    Assertions.assertEquals(request, result.getApplicant());
    Assertions.assertEquals(requestExp.getDateRange().getFromDate(), result.getFromDate());
    Assertions.assertEquals(requestExp.getDateRange().getToDate(), result.getToDate());
    Assertions.assertEquals(requestExp.getCompany().getCompanyName(), result.getCompanyName());
    Assertions.assertEquals(requestExp.getOccupation(), result.getOccupation());
  }
}
