package muni.fi.cz.jobportal.api.common;

import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.Data;
import muni.fi.cz.jobportal.annotation.DateRange;

@Data
public class ExperienceDto {

  private UUID id;
  @DateRange
  private DateRangeDto dateRange;
  private CompanyDto company;
  @NotNull
  private String occupation;
  private List<@NotNull ReferenceDto> jobCategories;
}
