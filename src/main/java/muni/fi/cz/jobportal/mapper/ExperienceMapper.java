package muni.fi.cz.jobportal.mapper;

import java.util.List;
import muni.fi.cz.jobportal.api.common.ExperienceDto;
import muni.fi.cz.jobportal.domain.Applicant;
import muni.fi.cz.jobportal.domain.Experience;
import muni.fi.cz.jobportal.factory.ExperienceFactory;
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
