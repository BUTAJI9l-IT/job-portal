package muni.fi.cz.jobportal.configuration.search.binder;

import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.APPLICANT;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.APPLICANT_NAME;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.COMPANY;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.COMPANY_NAME;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.JOB_POSITION;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.JOB_POSITION_NAME;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.SORT_SUFFIX;
import static muni.fi.cz.jobportal.configuration.search.LuceneConfiguration.KEYWORD_ANALYZER;
import static muni.fi.cz.jobportal.configuration.search.LuceneConfiguration.SORT_NORMALIZER;

import muni.fi.cz.jobportal.domain.Application;
import muni.fi.cz.jobportal.domain.Application_;
import org.hibernate.search.engine.backend.document.DocumentElement;
import org.hibernate.search.engine.backend.document.IndexFieldReference;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.bridge.TypeBridge;
import org.hibernate.search.mapper.pojo.bridge.binding.TypeBindingContext;
import org.hibernate.search.mapper.pojo.bridge.mapping.programmatic.TypeBinder;
import org.hibernate.search.mapper.pojo.bridge.runtime.TypeBridgeWriteContext;

public class ApplicationBinder implements TypeBinder {

  @Override
  public void bind(TypeBindingContext typeBindingContext) {
    typeBindingContext.dependencies()
      .use(Application_.APPLICANT)
      .use(Application_.JOB_POSITION);

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

    final var jobPosition = typeBindingContext.indexSchemaElement()
      .field(JOB_POSITION, f -> f.asString()
        .analyzer(KEYWORD_ANALYZER)
        .searchAnalyzer(KEYWORD_ANALYZER))
      .toReference();
    final var jobPositionSort = typeBindingContext.indexSchemaElement()
      .field(JOB_POSITION + SORT_SUFFIX, f -> f.asString()
        .sortable(Sortable.YES)
        .normalizer(SORT_NORMALIZER))
      .toReference();

    final var applicant = typeBindingContext.indexSchemaElement()
      .field(APPLICANT, f -> f.asString()
        .analyzer(KEYWORD_ANALYZER)
        .searchAnalyzer(KEYWORD_ANALYZER))
      .toReference();
    final var applicantSort = typeBindingContext.indexSchemaElement()
      .field(APPLICANT + SORT_SUFFIX, f -> f.asString()
        .sortable(Sortable.YES)
        .normalizer(SORT_NORMALIZER))
      .toReference();

    final var companyName = typeBindingContext.indexSchemaElement()
      .field(COMPANY_NAME, f -> f.asString()
        .analyzer(KEYWORD_ANALYZER)
        .searchAnalyzer(KEYWORD_ANALYZER))
      .toReference();

    final var jobPositionName = typeBindingContext.indexSchemaElement()
      .field(JOB_POSITION_NAME, f -> f.asString()
        .analyzer(KEYWORD_ANALYZER)
        .searchAnalyzer(KEYWORD_ANALYZER))
      .toReference();

    final var applicantName = typeBindingContext.indexSchemaElement()
      .field(APPLICANT_NAME, f -> f.asString()
        .analyzer(KEYWORD_ANALYZER)
        .searchAnalyzer(KEYWORD_ANALYZER))
      .toReference();

    typeBindingContext.bridge(Application.class,
      new Bridge(company, companySort, jobPosition, jobPositionSort, applicant, applicantSort, companyName,
        jobPositionName, applicantName));
  }

  private record Bridge(IndexFieldReference<String> company, IndexFieldReference<String> companySort,
                        IndexFieldReference<String> jobPosition, IndexFieldReference<String> jobPositionSort,
                        IndexFieldReference<String> applicant, IndexFieldReference<String> applicantSort,
                        IndexFieldReference<String> companyName, IndexFieldReference<String> jobPositionName,
                        IndexFieldReference<String> applicantName) implements TypeBridge<Application> {

    @Override
    public void write(DocumentElement target, Application application, TypeBridgeWriteContext context) {
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
