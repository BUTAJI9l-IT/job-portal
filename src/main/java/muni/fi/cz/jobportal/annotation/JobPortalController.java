package muni.fi.cz.jobportal.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.springframework.web.bind.annotation.RestController;

/**
 * Annotation for the application controller classes.
 *
 * @author Vitalii Bortsov
 */
@Target(TYPE)
@Retention(RUNTIME)
@RestController
public @interface JobPortalController {

}
