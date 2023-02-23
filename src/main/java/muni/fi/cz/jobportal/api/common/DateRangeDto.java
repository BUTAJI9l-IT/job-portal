package muni.fi.cz.jobportal.api.common;

import java.time.LocalDate;
import lombok.Data;

@Data
public class DateRangeDto {

  private LocalDate from;
  private LocalDate to;
}
