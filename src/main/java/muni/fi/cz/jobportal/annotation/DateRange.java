package muni.fi.cz.jobportal.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import muni.fi.cz.jobportal.utils.validation.DateRangeValidator;

@Constraint(validatedBy = DateRangeValidator.class)
@Target(FIELD)
@Retention(RUNTIME)
public @interface DateRange {

  String message() default "Wrong date range";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
