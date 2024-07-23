package muni.fi.cz.jobportal.configuration.search.binder;

import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import muni.fi.cz.jobportal.configuration.constants.SearchProperties;
import muni.fi.cz.jobportal.domain.Applicant;
import muni.fi.cz.jobportal.domain.Applicant_;
import muni.fi.cz.jobportal.domain.Application;
import org.hibernate.search.engine.backend.document.DocumentElement;
import org.hibernate.search.engine.backend.document.IndexFieldReference;
import org.hibernate.search.mapper.pojo.bridge.TypeBridge;
import org.hibernate.search.mapper.pojo.bridge.binding.TypeBindingContext;
import org.hibernate.search.mapper.pojo.bridge.runtime.TypeBridgeWriteContext;

/**
 * Type binder for applicant entity.
 *
 * @author Vitalii Bortsov
 */
public class ApplicantBinder extends AbstractBinder {

  @Override
  public void bind(TypeBindingContext typeBindingContext) {
    typeBindingContext.dependencies()
      .use(Applicant_.USER)
      .use(Applicant_.APPLICATIONS);

    final var fullName = fulltext(typeBindingContext, SearchProperties.NAME, String.class);
    final var fullNameSort = sort(typeBindingContext, SearchProperties.NAME, String.class);
    final var jobPositionId = keywordCollection(typeBindingContext, SearchProperties.JOB_POSITION,
      String.class);
    final var jobPositionFulltext = fulltextCollection(typeBindingContext,
      SearchProperties.JOB_POSITION, String.class);
    final var jobPositionSort = sortCollection(typeBindingContext, SearchProperties.JOB_POSITION,
      String.class);

    typeBindingContext.bridge(Applicant.class,
      new Bridge(fullName, fullNameSort, jobPositionId, jobPositionFulltext, jobPositionSort));
  }

  private record Bridge(IndexFieldReference<String> fullName,
                        IndexFieldReference<String> fullNameSort,
                        IndexFieldReference<String> jobPositionId,
                        IndexFieldReference<String> jobPositionFulltext,
                        IndexFieldReference<String> jobPositionSort) implements
    TypeBridge<Applicant> {

    @Override
    public void write(DocumentElement target, Applicant applicant, TypeBridgeWriteContext context) {
      final var name = applicant.getUser().getFullName();
      target.addValue(fullName, name);
      target.addValue(fullNameSort, name);
      if (CollectionUtils.isNotEmpty(applicant.getApplications())) {
        applicant.getApplications().stream().map(Application::getJobPosition).forEach(position -> {
          target.addValue(jobPositionId, position.getId().toString());
          target.addValue(jobPositionFulltext, position.getPositionName());
          target.addValue(jobPositionSort, position.getPositionName());
        });
      }
    }
  }
}
