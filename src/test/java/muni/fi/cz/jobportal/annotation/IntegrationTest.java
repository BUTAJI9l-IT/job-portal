package muni.fi.cz.jobportal.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Target(TYPE)
@Retention(RUNTIME)
@SpringBootTest
@ActiveProfiles("it")
@Transactional
public @interface IntegrationTest {

}
