package muni.fi.cz.jobportal.configuration.search.binder;

import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.CATEGORY;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.COMPANY;

import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import muni.fi.cz.jobportal.domain.JobPosition;
import muni.fi.cz.jobportal.domain.JobPosition_;
import org.hibernate.search.engine.backend.document.DocumentElement;
import org.hibernate.search.engine.backend.document.IndexFieldReference;
import org.hibernate.search.mapper.pojo.bridge.TypeBridge;
import org.hibernate.search.mapper.pojo.bridge.binding.TypeBindingContext;
import org.hibernate.search.mapper.pojo.bridge.runtime.TypeBridgeWriteContext;

public class JobPositionBinder extends AbstractBinder {

  @Override
  public void bind(TypeBindingContext typeBindingContext) {
    typeBindingContext.dependencies()
      .use(JobPosition_.COMPANY)
      .use(JobPosition_.JOB_CATEGORIES);

    final var company = keyword(typeBindingContext, COMPANY, String.class);
    final var companySort = sort(typeBindingContext, COMPANY, String.class);
    final var categories = keywordCollection(typeBindingContext, CATEGORY, String.class);
    final var categoriesSort = sortCollection(typeBindingContext, CATEGORY, String.class);

    typeBindingContext.bridge(JobPosition.class, new Bridge(company, companySort, categories, categoriesSort));
  }

  private record Bridge(IndexFieldReference<String> company, IndexFieldReference<String> companySort,
                        IndexFieldReference<String> categories,
                        IndexFieldReference<String> categoriesSort) implements TypeBridge<JobPosition> {

    @Override
    public void write(DocumentElement target, JobPosition jobPosition, TypeBridgeWriteContext context) {
      if (CollectionUtils.isNotEmpty(jobPosition.getJobCategories())) {
        jobPosition.getJobCategories().forEach(jpc -> {
          target.addValue(categories, jpc.getId().toString());
          target.addValue(categoriesSort, jpc.getName());
        });
      }
      target.addValue(company, jobPosition.getCompany().getId().toString());
      target.addValue(companySort, jobPosition.getCompany().getCompanyName());
    }
  }
}
