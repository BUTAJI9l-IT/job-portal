package muni.fi.cz.jobportal.annotation.search;

import static muni.fi.cz.jobportal.annotation.search.DateQueryField.RangeSide.FROM;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

/**
 * Annotation for processing search parameters fields that contain date range parts (from-to).
 *
 * @author Vitalii Bortsov
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@KeywordQueryField
public @interface DateQueryField {

  @AliasFor(annotation = KeywordQueryField.class, attribute = "value")
  String value() default "";

  RangeSide side() default FROM;

  enum RangeSide {
    FROM, TO
  }
}
