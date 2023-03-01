package muni.fi.cz.jobportal.utils.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import muni.fi.cz.jobportal.annotation.DateRange;
import muni.fi.cz.jobportal.api.common.DateRangeDto;

public class DateRangeValidator implements ConstraintValidator<DateRange, DateRangeDto> {

  @Override
  public boolean isValid(DateRangeDto value, ConstraintValidatorContext cxt) {
    if (value.getToDate() == null) {
      return true;
    }
    if (value.getFromDate() == null) {
      return false;
    }
    return value.getToDate().isAfter(value.getFromDate());
  }

}
