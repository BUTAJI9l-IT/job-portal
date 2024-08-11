package com.github.butaji9l.jobportal.be.mapper;

import com.github.butaji9l.jobportal.be.api.common.ExperienceDto;
import com.github.butaji9l.jobportal.be.domain.Applicant;
import com.github.butaji9l.jobportal.be.domain.Experience;
import com.github.butaji9l.jobportal.be.factory.ExperienceFactory;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper for experiences
 *
 * @author Vitalii Bortsov
 */
@Mapper(uses = ExperienceFactory.class)
public interface ExperienceMapper {

  @Mapping(target = "dateRange.fromDate", source = "fromDate")
  @Mapping(target = "dateRange.toDate", source = "toDate")
  @Mapping(target = "company", ignore = true)
  ExperienceDto map(Experience source);

  List<ExperienceDto> map(List<Experience> source);

  @AfterMapping
  default List<ExperienceDto> sortByStartDate(@MappingTarget List<ExperienceDto> experiences) {
    experiences.sort((o1, o2) -> {
      if (o1.getDateRange() == null || o2.getDateRange() == null) {
        return 0;
      }
      if (o1.getDateRange().getFromDate() == null) {
        return 1;
      }
      if (o2.getDateRange().getFromDate() == null) {
        return -1;
      }
      return o2.getDateRange().getFromDate().compareTo(o1.getDateRange().getFromDate());
    });
    return experiences;
  }

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "company", ignore = true)
  @Mapping(target = "jobCategories", ignore = true)
  @Mapping(target = "fromDate", source = "source.dateRange.fromDate")
  @Mapping(target = "toDate", source = "source.dateRange.toDate")
  @Mapping(target = "companyName", source = "source.company.companyName")
  @Mapping(target = "applicant", source = "applicant")
  Experience create(ExperienceDto source, Applicant applicant);
}
