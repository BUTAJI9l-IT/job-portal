package com.github.butaji9l.jobportal.be.annotation;

import static com.github.butaji9l.jobportal.be.configuration.constants.ApplicationConstants.BEARER_AUTH;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

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
