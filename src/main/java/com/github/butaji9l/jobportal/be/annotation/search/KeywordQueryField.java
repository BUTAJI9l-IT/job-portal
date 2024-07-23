package com.github.butaji9l.jobportal.be.annotation.search;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for processing search parameters fields.
 *
 * @author Vitalii Bortsov
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface KeywordQueryField {

  String value() default "";

  boolean generic() default false;
}
