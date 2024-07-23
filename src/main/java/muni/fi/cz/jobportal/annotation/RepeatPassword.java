package muni.fi.cz.jobportal.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import muni.fi.cz.jobportal.utils.validation.RepeatPasswordValidator;

/**
 * Constraint annotation for validating password input.
 *
 * @author Vitalii Bortsov
 */
@Constraint(validatedBy = RepeatPasswordValidator.class)
@Target(FIELD)
@Retention(RUNTIME)
public @interface RepeatPassword {

  String message() default "Passwords do not match!";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
