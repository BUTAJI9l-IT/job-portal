package com.github.butaji9l.jobportal.be.api.common;

import com.github.butaji9l.jobportal.be.annotation.DateRange;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.Data;

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
