package muni.fi.cz.jobportal.annotation;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Abstract Integration Test Annotations
 *
 * @author Vitalii Bortsov
 */
@Target(TYPE)
@Retention(RUNTIME)
@SpringBootTest
@ActiveProfiles("it")
@Transactional
@WithMockUser
@ExtendWith(MockitoExtension.class)
@Testcontainers
@Inherited
public @interface IntegrationTest {

}
