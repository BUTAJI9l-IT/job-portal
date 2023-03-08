package muni.fi.cz.jobportal.event.handler;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.domain.Applicant;
import muni.fi.cz.jobportal.domain.Application;
import muni.fi.cz.jobportal.domain.Company;
import muni.fi.cz.jobportal.domain.JobPosition;
import muni.fi.cz.jobportal.domain.Occupation;
import muni.fi.cz.jobportal.domain.User;
import org.hibernate.search.mapper.orm.Search;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OnApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

  @PersistenceContext
  private final EntityManager entityManager;

  /**
   * Create an initial Lucene index for the data already present in the database. This method is called when Spring's
   * startup.
   */
  @Override
  @Transactional
  public void onApplicationEvent(@NonNull final ApplicationReadyEvent event) {
    Search.session(entityManager)
      .massIndexer(Application.class, Applicant.class, JobPosition.class, User.class, Company.class, Occupation.class)
      .start();
  }
}
