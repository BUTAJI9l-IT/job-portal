package com.github.butaji9l.jobportal.be.configuration.search.binder;

import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.CATEGORY;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.COMPANY;

import com.github.butaji9l.jobportal.be.domain.JobPosition;
import com.github.butaji9l.jobportal.be.domain.JobPosition_;
import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import org.hibernate.search.engine.backend.document.DocumentElement;
import org.hibernate.search.engine.backend.document.IndexFieldReference;
import org.hibernate.search.mapper.pojo.bridge.TypeBridge;
import org.hibernate.search.mapper.pojo.bridge.binding.TypeBindingContext;
import org.hibernate.search.mapper.pojo.bridge.runtime.TypeBridgeWriteContext;

/**
 * Type binder for job position entity.
 *
 * @author Vitalii Bortsov
 */
public class JobPositionBinder extends AbstractBinder {

  @Override
  public void bind(TypeBindingContext typeBindingContext) {
    typeBindingContext.dependencies()
      .use(JobPosition_.COMPANY)
      .use(JobPosition_.JOB_CATEGORIES);

    final var company = keyword(typeBindingContext, COMPANY, String.class);
    final var companyFulltext = fulltext(typeBindingContext, COMPANY, String.class);
    final var companySort = sort(typeBindingContext, COMPANY, String.class);
    final var categories = keywordCollection(typeBindingContext, CATEGORY, String.class);
    final var categoriesSort = sortCollection(typeBindingContext, CATEGORY, String.class);

    typeBindingContext.bridge(JobPosition.class,
      new Bridge(company, companyFulltext, companySort, categories, categoriesSort));
  }

  private record Bridge(IndexFieldReference<String> company,
                        IndexFieldReference<String> companyFulltext,
                        IndexFieldReference<String> companySort,
                        IndexFieldReference<String> categories,
                        IndexFieldReference<String> categoriesSort) implements
    TypeBridge<JobPosition> {

    @Override
    public void write(DocumentElement target, JobPosition jobPosition,
      TypeBridgeWriteContext context) {
      if (CollectionUtils.isNotEmpty(jobPosition.getJobCategories())) {
        jobPosition.getJobCategories().forEach(jpc -> {
          target.addValue(categories, jpc.getId().toString());
          target.addValue(categoriesSort, jpc.getName());
        });
      }
      final var positionCompany = jobPosition.getCompany();
      target.addValue(company, positionCompany.getId().toString());
      target.addValue(companyFulltext, positionCompany.getCompanyName());
      target.addValue(companySort, positionCompany.getCompanyName());
    }
  }
}
