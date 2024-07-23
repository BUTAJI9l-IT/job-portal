package com.github.butaji9l.jobportal.be.configuration.search.binder;

import com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties;
import com.github.butaji9l.jobportal.be.domain.User;
import com.github.butaji9l.jobportal.be.domain.User_;
import org.hibernate.search.engine.backend.document.DocumentElement;
import org.hibernate.search.engine.backend.document.IndexFieldReference;
import org.hibernate.search.mapper.pojo.bridge.TypeBridge;
import org.hibernate.search.mapper.pojo.bridge.binding.TypeBindingContext;
import org.hibernate.search.mapper.pojo.bridge.runtime.TypeBridgeWriteContext;

/**
 * Type binder for user entity.
 *
 * @author Vitalii Bortsov
 */
public class UserBinder extends AbstractBinder {

  @Override
  public void bind(TypeBindingContext typeBindingContext) {
    typeBindingContext.dependencies()
      .use(User_.NAME)
      .use(User_.LAST_NAME);

    final var fullName = fulltext(typeBindingContext, SearchProperties.NAME, String.class);
    final var fullNameSort = sort(typeBindingContext, SearchProperties.NAME, String.class);

    typeBindingContext.bridge(User.class, new Bridge(fullName, fullNameSort));
  }

  private record Bridge(IndexFieldReference<String> fullNameField,
                        IndexFieldReference<String> fullNameSort) implements
    TypeBridge<User> {

    @Override
    public void write(DocumentElement target, User user, TypeBridgeWriteContext context) {
      final var fullName = user.getFullName();
      target.addValue(this.fullNameField, fullName);
      target.addValue(this.fullNameSort, fullName);
    }
  }
}
