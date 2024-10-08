package com.github.butaji9l.jobportal.be.configuration.search.binder;

import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.APPLICANT;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.COMPANY;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.JOB_POSITION;

import com.github.butaji9l.jobportal.be.domain.Application;
import com.github.butaji9l.jobportal.be.domain.Application_;
import org.hibernate.search.engine.backend.document.DocumentElement;
import org.hibernate.search.engine.backend.document.IndexFieldReference;
import org.hibernate.search.mapper.pojo.bridge.TypeBridge;
import org.hibernate.search.mapper.pojo.bridge.binding.TypeBindingContext;
import org.hibernate.search.mapper.pojo.bridge.runtime.TypeBridgeWriteContext;

/**
 * Type binder for application entity.
 *
 * @author Vitalii Bortsov
 */
public class ApplicationBinder extends AbstractBinder {

  @Override
  public void bind(TypeBindingContext typeBindingContext) {
    typeBindingContext.dependencies()
      .use(Application_.APPLICANT)
      .use(Application_.JOB_POSITION);

    final var company = keyword(typeBindingContext, COMPANY, String.class);
    final var companySort = sort(typeBindingContext, COMPANY, String.class);
    final var jobPosition = keyword(typeBindingContext, JOB_POSITION, String.class);
    final var jobPositionSort = sort(typeBindingContext, JOB_POSITION, String.class);
    final var applicant = keyword(typeBindingContext, APPLICANT, String.class);
    final var applicantSort = sort(typeBindingContext, APPLICANT, String.class);
    final var companyName = fulltext(typeBindingContext, COMPANY, String.class);
    final var jobPositionName = fulltext(typeBindingContext, JOB_POSITION, String.class);
    final var applicantName = fulltext(typeBindingContext, APPLICANT, String.class);

    typeBindingContext.bridge(Application.class,
      new Bridge(company, companySort, jobPosition, jobPositionSort, applicant, applicantSort,
        companyName,
        jobPositionName, applicantName));
  }

  private record Bridge(IndexFieldReference<String> company,
                        IndexFieldReference<String> companySort,
                        IndexFieldReference<String> jobPosition,
                        IndexFieldReference<String> jobPositionSort,
                        IndexFieldReference<String> applicant,
                        IndexFieldReference<String> applicantSort,
                        IndexFieldReference<String> companyName,
                        IndexFieldReference<String> jobPositionName,
                        IndexFieldReference<String> applicantName) implements
    TypeBridge<Application> {

    @Override
    public void write(DocumentElement target, Application application,
      TypeBridgeWriteContext context) {
      final var position = application.getJobPosition();
      final var comp = position.getCompany();
      final var a = application.getApplicant();
      target.addValue(company, comp.getId().toString());
      target.addValue(companySort, comp.getCompanyName());
      target.addValue(companyName, comp.getCompanyName());
      target.addValue(applicant, a.getId().toString());
      target.addValue(applicantSort, a.getUser().getFullName());
      target.addValue(applicantName, a.getUser().getFullName());
      target.addValue(jobPosition, position.getId().toString());
      target.addValue(jobPositionSort, position.getPositionName());
      target.addValue(jobPositionName, position.getPositionName());
    }
  }
}
