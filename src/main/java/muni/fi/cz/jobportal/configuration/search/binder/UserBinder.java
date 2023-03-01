package muni.fi.cz.jobportal.configuration.search.binder;

import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.SORT_SUFFIX;
import static muni.fi.cz.jobportal.configuration.search.LuceneConfiguration.FULLTEXT_ANALYZER;
import static muni.fi.cz.jobportal.configuration.search.LuceneConfiguration.SORT_NORMALIZER;
import static muni.fi.cz.jobportal.configuration.search.LuceneConfiguration.SUGGESTER;

import muni.fi.cz.jobportal.configuration.constants.SearchProperties;
import muni.fi.cz.jobportal.domain.User;
import muni.fi.cz.jobportal.domain.User_;
import org.hibernate.search.engine.backend.document.DocumentElement;
import org.hibernate.search.engine.backend.document.IndexFieldReference;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.bridge.TypeBridge;
import org.hibernate.search.mapper.pojo.bridge.binding.TypeBindingContext;
import org.hibernate.search.mapper.pojo.bridge.mapping.programmatic.TypeBinder;
import org.hibernate.search.mapper.pojo.bridge.runtime.TypeBridgeWriteContext;

public class UserBinder implements TypeBinder {

  @Override
  public void bind(TypeBindingContext typeBindingContext) {
    typeBindingContext.dependencies()
      .use(User_.NAME)
      .use(User_.LAST_NAME);
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
    typeBindingContext.bridge(User.class, new Bridge(fullName, fullNameSort));
  }

  private record Bridge(IndexFieldReference<String> fullNameField, IndexFieldReference<String> fullNameSort) implements
    TypeBridge<User> {

    @Override
    public void write(DocumentElement target, User user, TypeBridgeWriteContext context) {
      final var fullName = user.getFullName();
      target.addValue(this.fullNameField, fullName);
      target.addValue(this.fullNameSort, fullName);
    }
  }
}
