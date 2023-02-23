package muni.fi.cz.jobportal.api.common;

import java.util.List;
import lombok.Data;
import muni.fi.cz.jobportal.annotation.DateRange;

@Data
public class ExperienceDto {

  @DateRange
  private DateRangeDto dateRange;
  private CompanyDto company;
  private List<ReferenceDto> jobCategories;
}
