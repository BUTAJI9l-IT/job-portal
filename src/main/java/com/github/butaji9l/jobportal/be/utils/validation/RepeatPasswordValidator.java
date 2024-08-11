package com.github.butaji9l.jobportal.be.utils.validation;

import com.github.butaji9l.jobportal.be.annotation.RepeatPassword;
import com.github.butaji9l.jobportal.be.api.common.RepeatPasswordDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * Validator for {@link RepeatPassword} annotation.
 *
 * @author Vitalii Bortsov
 */
public class RepeatPasswordValidator implements
  ConstraintValidator<RepeatPassword, RepeatPasswordDto> {

  @Override
  public boolean isValid(RepeatPasswordDto value, ConstraintValidatorContext cxt) {
    return value != null && Objects.equals(value.getPassword(), value.getRepeatPassword());
  }

}


