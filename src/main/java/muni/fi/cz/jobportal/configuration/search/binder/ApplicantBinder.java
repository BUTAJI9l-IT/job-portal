package muni.fi.cz.jobportal.configuration.search.binder;

import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.JOB_POSITION;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.SORT_SUFFIX;
import static muni.fi.cz.jobportal.configuration.search.LuceneConfiguration.FULLTEXT_ANALYZER;
import static muni.fi.cz.jobportal.configuration.search.LuceneConfiguration.KEYWORD_ANALYZER;
import static muni.fi.cz.jobportal.configuration.search.LuceneConfiguration.SORT_NORMALIZER;
import static muni.fi.cz.jobportal.configuration.search.LuceneConfiguration.SUGGESTER;

import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import muni.fi.cz.jobportal.configuration.constants.SearchProperties;
import muni.fi.cz.jobportal.domain.Applicant;
import muni.fi.cz.jobportal.domain.Applicant_;
import org.hibernate.search.engine.backend.document.DocumentElement;
import org.hibernate.search.engine.backend.document.IndexFieldReference;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.bridge.TypeBridge;
import org.hibernate.search.mapper.pojo.bridge.binding.TypeBindingContext;
import org.hibernate.search.mapper.pojo.bridge.mapping.programmatic.TypeBinder;
import org.hibernate.search.mapper.pojo.bridge.runtime.TypeBridgeWriteContext;

public class ApplicantBinder implements TypeBinder {

  @Override
  public void bind(TypeBindingContext typeBindingContext) {
    typeBindingContext.dependencies()
      .use(Applicant_.USER)
      .use(Applicant_.APPLICATIONS);

    final var fullName = typeBindingContext.indexSchemaElement()
      .field(SearchProperties.NAME, f -> f.asString()
        .analyzer(FULLTEXT_ANALYZER)
        .searchAnalyzer(SUGGESTER)
      )
      .toReference();
    final var fullNameSort = typeBindingContext.indexSchemaElement()
      .field(SearchProperties.NAME + SORT_SUFFIX, f -> f.asString()
        .sortable(Sortable.YES).normalizer(SORT_NORMALIZER)
      ).toReference();
    final var jobPosition = typeBindingContext.indexSchemaElement()
      .field(JOB_POSITION, f -> f.asString()
        .analyzer(KEYWORD_ANALYZER)
        .searchAnalyzer(KEYWORD_ANALYZER))
      .multiValued()
      .toReference();
    final var jobPositionSort = typeBindingContext.indexSchemaElement()
      .field(JOB_POSITION + SORT_SUFFIX, f -> f.asString()
        .sortable(Sortable.YES)
        .normalizer(SORT_NORMALIZER))
      .multiValued()
      .toReference();

    typeBindingContext.bridge(Applicant.class, new Bridge(fullName, fullNameSort, jobPosition, jobPositionSort));
  }

  private record Bridge(IndexFieldReference<String> fullName, IndexFieldReference<String> fullNameSort,
                        IndexFieldReference<String> jobPosition, IndexFieldReference<String> jobPositionSort) implements
    TypeBridge<Applicant> {

    @Override
    public void write(DocumentElement target, Applicant applicant, TypeBridgeWriteContext context) {
      final var name = applicant.getUser().getFullName();
      target.addValue(fullName, name);
      target.addValue(fullNameSort, name);
      if (CollectionUtils.isNotEmpty(applicant.getApplications())) {
        applicant.getApplications().forEach(application -> {
          target.addValue(jobPosition, application.getJobPosition().getId().toString());
          target.addValue(jobPositionSort, application.getJobPosition().getPositionName());
        });
      }
    }
  }
}
