package muni.fi.cz.jobportal.api.common;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DateRangeDto {

  private LocalDate fromDate;
  private LocalDate toDate;
}
