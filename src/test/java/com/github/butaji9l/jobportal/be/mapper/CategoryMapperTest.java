package com.github.butaji9l.jobportal.be.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.butaji9l.jobportal.be.AbstractTest;
import com.github.butaji9l.jobportal.be.domain.JobCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

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
