package com.github.butaji9l.jobportal.be.api.common;

import java.time.LocalDate;
import lombok.Data;

@Data
public class DateRangeDto {

  private LocalDate fromDate;
  private LocalDate toDate;
}
