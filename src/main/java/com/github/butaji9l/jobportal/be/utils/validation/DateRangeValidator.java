package com.github.butaji9l.jobportal.be.utils.validation;

import com.github.butaji9l.jobportal.be.annotation.DateRange;
import com.github.butaji9l.jobportal.be.api.common.DateRangeDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator for {@link DateRange} annotation.
 *
 * @author Vitalii Bortsov
 */
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
