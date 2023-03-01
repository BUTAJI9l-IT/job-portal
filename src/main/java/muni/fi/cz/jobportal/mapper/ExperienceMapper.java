package muni.fi.cz.jobportal.mapper;

import muni.fi.cz.jobportal.api.common.ExperienceDto;
import muni.fi.cz.jobportal.domain.Applicant;
import muni.fi.cz.jobportal.domain.Experience;
import muni.fi.cz.jobportal.factory.ExperienceFactory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {ExperienceFactory.class, CompanyMapper.class})
public interface ExperienceMapper {

  @Mapping(target = "dateRange.fromDate", source = "fromDate")
  @Mapping(target = "dateRange.toDate", source = "toDate")
  @Mapping(target = "company.companyName", source = "companyName")
  ExperienceDto map(Experience source);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "company", ignore = true)
  @Mapping(target = "jobCategories", ignore = true)
  @Mapping(target = "fromDate", source = "source.dateRange.fromDate")
  @Mapping(target = "toDate", source = "source.dateRange.toDate")
  @Mapping(target = "companyName", source = "source.company.companyName")
  @Mapping(target = "applicant", source = "applicant")
  Experience create(ExperienceDto source, Applicant applicant);
}
