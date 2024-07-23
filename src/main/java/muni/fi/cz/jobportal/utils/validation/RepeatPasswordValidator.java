package muni.fi.cz.jobportal.utils.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import muni.fi.cz.jobportal.annotation.RepeatPassword;
import muni.fi.cz.jobportal.api.common.RepeatPasswordDto;

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


