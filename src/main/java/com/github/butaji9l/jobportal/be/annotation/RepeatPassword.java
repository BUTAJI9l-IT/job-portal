package com.github.butaji9l.jobportal.be.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.github.butaji9l.jobportal.be.utils.validation.RepeatPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

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
