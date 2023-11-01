package muni.fi.cz.jobportal.mapper;

import muni.fi.cz.jobportal.AbstractTest;
import muni.fi.cz.jobportal.api.common.ExperienceDto;
import muni.fi.cz.jobportal.domain.Applicant;
import muni.fi.cz.jobportal.domain.Experience;
import muni.fi.cz.jobportal.factory.ExperienceFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

    assertEquals(request.getFromDate(), result.getDateRange().getFromDate());
    assertEquals(request.getToDate(), result.getDateRange().getToDate());
    assertEquals(request.getId(), result.getId());
    assertEquals(request.getOccupation(), result.getOccupation());
    assertEquals(request.getJobCategories().size(), result.getJobCategories().size());
  }

  @Test
  void createTest() {
    final var request = loadResource("applicant_entity.json", Applicant.class);
    final var requestExp = loadResource("experience_dto.json", ExperienceDto.class);

    when(experienceFactory.prepare(any(ExperienceDto.class))).thenReturn(new Experience());

    final var result = experienceMapper.create(requestExp, request);

    assertEquals(request, result.getApplicant());
    assertEquals(requestExp.getDateRange().getFromDate(), result.getFromDate());
    assertEquals(requestExp.getDateRange().getToDate(), result.getToDate());
    assertEquals(requestExp.getCompany().getCompanyName(), result.getCompanyName());
    assertEquals(requestExp.getOccupation(), result.getOccupation());
  }
}
