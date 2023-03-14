package muni.fi.cz.jobportal.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static muni.fi.cz.jobportal.configuration.constants.ApplicationConstants.BEARER_AUTH;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.springframework.web.bind.annotation.RestController;

/**
 * Annotation for the application controller classes.
 *
 * @author Vitalii Bortsov
 */
@SecurityRequirement(name = BEARER_AUTH)
@Target(TYPE)
@Retention(RUNTIME)
@RestController
public @interface JobPortalSecuredController {

}
