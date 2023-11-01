package muni.fi.cz.jobportal.mapper;

import muni.fi.cz.jobportal.AbstractTest;
import muni.fi.cz.jobportal.domain.JobCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CategoryMapperTest extends AbstractTest {

  @InjectMocks
  private CategoryMapperImpl categoryMapper;

  @Test
  void mapDtoTest() {
    final var request = loadResource("job_category_entity.json", JobCategory.class);
    final var result = categoryMapper.map(request);
    assertEquals(result.getId(), request.getId());
    assertEquals(result.getName(), request.getName());
    assertEquals(result.getOccupations().size(), request.getOccupations().size());
  }

}
