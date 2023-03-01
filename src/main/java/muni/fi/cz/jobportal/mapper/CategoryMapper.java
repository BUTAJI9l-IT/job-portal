package muni.fi.cz.jobportal.mapper;

import muni.fi.cz.jobportal.api.common.CategoryDto;
import muni.fi.cz.jobportal.domain.JobCategory;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {

  CategoryDto map(JobCategory source);
}
