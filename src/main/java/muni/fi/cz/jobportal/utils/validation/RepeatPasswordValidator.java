package muni.fi.cz.jobportal.utils.validation;

import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import muni.fi.cz.jobportal.annotation.RepeatPassword;
import muni.fi.cz.jobportal.api.common.RepeatPasswordDto;

public class RepeatPasswordValidator implements ConstraintValidator<RepeatPassword, RepeatPasswordDto> {

  @Override
  public boolean isValid(RepeatPasswordDto value, ConstraintValidatorContext cxt) {
    return Objects.equals(value.getPassword(), value.getRepeatPassword());
  }

}


