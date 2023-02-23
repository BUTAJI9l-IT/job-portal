package muni.fi.cz.jobportal.utils.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import muni.fi.cz.jobportal.annotation.DateRange;
import muni.fi.cz.jobportal.api.common.DateRangeDto;

public class DateRangeValidator implements ConstraintValidator<DateRange, DateRangeDto> {

  @Override
  public boolean isValid(DateRangeDto value, ConstraintValidatorContext cxt) {
    if (value.getTo() == null) {
      return true;
    }
    if (value.getFrom() == null) {
      return false;
    }
    return value.getTo().isAfter(value.getFrom());
  }

}
