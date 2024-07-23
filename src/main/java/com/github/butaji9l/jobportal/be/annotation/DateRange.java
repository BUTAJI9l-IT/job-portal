package com.github.butaji9l.jobportal.be.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.github.butaji9l.jobportal.be.utils.validation.DateRangeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Constraint annotation for validating date range (from-to) input.
 *
 * @author Vitalii Bortsov
 */
@Constraint(validatedBy = DateRangeValidator.class)
@Target(FIELD)
@Retention(RUNTIME)
public @interface DateRange {

  String message() default "Wrong date range";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
