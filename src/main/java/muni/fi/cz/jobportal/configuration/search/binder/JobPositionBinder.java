package muni.fi.cz.jobportal.configuration.search.binder;

import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.CATEGORY;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.COMPANY;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.SORT_SUFFIX;
import static muni.fi.cz.jobportal.configuration.search.LuceneConfiguration.KEYWORD_ANALYZER;
import static muni.fi.cz.jobportal.configuration.search.LuceneConfiguration.SORT_NORMALIZER;

import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import muni.fi.cz.jobportal.domain.JobPosition;
import muni.fi.cz.jobportal.domain.JobPosition_;
import org.hibernate.search.engine.backend.document.DocumentElement;
import org.hibernate.search.engine.backend.document.IndexFieldReference;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.bridge.TypeBridge;
import org.hibernate.search.mapper.pojo.bridge.binding.TypeBindingContext;
import org.hibernate.search.mapper.pojo.bridge.mapping.programmatic.TypeBinder;
import org.hibernate.search.mapper.pojo.bridge.runtime.TypeBridgeWriteContext;

public class JobPositionBinder implements TypeBinder {

  @Override
  public void bind(TypeBindingContext typeBindingContext) {
    typeBindingContext.dependencies()
      .use(JobPosition_.COMPANY)
      .use(JobPosition_.JOB_CATEGORIES);

    final var company = typeBindingContext.indexSchemaElement()
      .field(COMPANY, f -> f.asString()
        .analyzer(KEYWORD_ANALYZER)
        .searchAnalyzer(KEYWORD_ANALYZER))
      .toReference();
    final var companySort = typeBindingContext.indexSchemaElement()
      .field(COMPANY + SORT_SUFFIX, f -> f.asString()
        .sortable(Sortable.YES)
        .normalizer(SORT_NORMALIZER))
      .toReference();
    final var categories = typeBindingContext.indexSchemaElement()
      .field(CATEGORY, f -> f.asString()
        .analyzer(KEYWORD_ANALYZER)
        .searchAnalyzer(KEYWORD_ANALYZER))
      .multiValued()
      .toReference();
    final var categoriesSort = typeBindingContext.indexSchemaElement()
      .field(CATEGORY + SORT_SUFFIX, f -> f.asString()
        .sortable(Sortable.YES)
        .normalizer(SORT_NORMALIZER))
      .multiValued()
      .toReference();
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
