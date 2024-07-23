package com.github.butaji9l.jobportal.be.mapper;

import com.github.butaji9l.jobportal.be.api.common.CategoryDto;
import com.github.butaji9l.jobportal.be.domain.JobCategory;
import org.mapstruct.Mapper;

/**
 * Mapper for job categories
 *
 * @author Vitalii Bortsov
 */
@Mapper
public interface CategoryMapper {

  CategoryDto map(JobCategory source);
}
