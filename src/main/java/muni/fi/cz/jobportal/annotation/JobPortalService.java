package muni.fi.cz.jobportal.annotation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation for the application service classes.
 *
 * @author Vitalii Bortsov
 */
@Target(TYPE)
@Retention(RUNTIME)
@Service
@Transactional
public @interface JobPortalService {

}
