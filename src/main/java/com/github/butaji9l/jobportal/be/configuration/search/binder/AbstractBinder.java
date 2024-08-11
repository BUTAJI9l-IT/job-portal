package com.github.butaji9l.jobportal.be.configuration.search.binder;

import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.FULLTEXT_SUFFIX;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.SORT_SUFFIX;
import static com.github.butaji9l.jobportal.be.configuration.search.LuceneConfiguration.FULLTEXT_ANALYZER;
import static com.github.butaji9l.jobportal.be.configuration.search.LuceneConfiguration.KEYWORD_ANALYZER;
import static com.github.butaji9l.jobportal.be.configuration.search.LuceneConfiguration.SORT_NORMALIZER;
import static com.github.butaji9l.jobportal.be.configuration.search.LuceneConfiguration.SUGGESTER;
import static org.hibernate.search.engine.backend.types.Sortable.YES;

import java.util.function.Function;
import org.hibernate.search.engine.backend.document.IndexFieldReference;
import org.hibernate.search.engine.backend.types.dsl.IndexFieldTypeFactory;
import org.hibernate.search.engine.backend.types.dsl.IndexFieldTypeFinalStep;
import org.hibernate.search.mapper.pojo.bridge.binding.TypeBindingContext;
import org.hibernate.search.mapper.pojo.bridge.mapping.programmatic.TypeBinder;

/**
 * Abstract type binder.
 *
 * @author Vitalii Bortsov
 */
public abstract class AbstractBinder implements TypeBinder {

  /**
   * Returns {@link IndexFieldReference} for given single field with fulltext analyzers applied.
   *
   * @param <T> Type of indexed field
   */
  protected <T> IndexFieldReference<T> fulltext(TypeBindingContext typeBindingContext,
    String fieldName,
    Class<T> clazz) {
    return getIndexFieldReference(typeBindingContext, fieldName + FULLTEXT_SUFFIX,
      fulltextAnalyzer(clazz), false);
  }

  /**
   * Returns {@link IndexFieldReference} for given collection field with fulltext analyzers
   * applied.
   *
   * @param <T> Type of indexed field
   */
  @SuppressWarnings("unused")
  protected <T> IndexFieldReference<T> fulltextCollection(TypeBindingContext typeBindingContext,
    String fieldName,
    Class<T> clazz) {
    return getIndexFieldReference(typeBindingContext, fieldName + FULLTEXT_SUFFIX,
      fulltextAnalyzer(clazz), true);
  }

  /**
   * Returns {@link IndexFieldReference} for given single field with keyword analyzers applied.
   *
   * @param <T> Type of indexed field
   */
  protected <T> IndexFieldReference<T> keyword(TypeBindingContext typeBindingContext,
    String fieldName,
    Class<T> clazz) {
    return getIndexFieldReference(typeBindingContext, fieldName, keywordAnalyzer(clazz), false);
  }

  /**
   * Returns {@link IndexFieldReference} for given collection field with keyword analyzers applied.
   *
   * @param <T> Type of indexed field
   */
  protected <T> IndexFieldReference<T> keywordCollection(TypeBindingContext typeBindingContext,
    String fieldName,
    Class<T> clazz) {
    return getIndexFieldReference(typeBindingContext, fieldName, keywordAnalyzer(clazz), true);
  }

  /**
   * Returns {@link IndexFieldReference} for given single field with sort analyzers applied.
   *
   * @param <T> Type of indexed field
   */
  protected <T> IndexFieldReference<T> sort(TypeBindingContext typeBindingContext, String fieldName,
    Class<T> clazz) {
    return getIndexFieldReference(typeBindingContext, fieldName + SORT_SUFFIX, sortAnalyzer(clazz),
      false);
  }

  /**
   * Returns {@link IndexFieldReference} for given collection field with sort analyzers applied.
   *
   * @param <T> Type of indexed field
   */
  protected <T> IndexFieldReference<T> sortCollection(TypeBindingContext typeBindingContext,
    String fieldName,
    Class<T> clazz) {
    return getIndexFieldReference(typeBindingContext, fieldName + SORT_SUFFIX, sortAnalyzer(clazz),
      true);
  }

  private static <T> IndexFieldReference<T> getIndexFieldReference(
    TypeBindingContext typeBindingContext,
    String fieldName,
    Function<? super IndexFieldTypeFactory, ? extends IndexFieldTypeFinalStep<T>> function,
    boolean multiValued) {
    final var field = typeBindingContext.indexSchemaElement().field(fieldName, function);
    return (multiValued ? field.multiValued() : field).toReference();
  }

  @SuppressWarnings("unchecked")
  private static <T> Function<? super IndexFieldTypeFactory, ? extends IndexFieldTypeFinalStep<T>> fulltextAnalyzer(
    Class<T> clazz) {
    if (clazz.equals(String.class)) {
      return f -> (IndexFieldTypeFinalStep<T>) f.asString().analyzer(FULLTEXT_ANALYZER)
        .searchAnalyzer(SUGGESTER);
    }
    return f -> f.as(clazz);
  }

  @SuppressWarnings("unchecked")
  private static <T> Function<? super IndexFieldTypeFactory, ? extends IndexFieldTypeFinalStep<T>> keywordAnalyzer(
    Class<T> clazz) {
    if (clazz.equals(String.class)) {
      return f -> (IndexFieldTypeFinalStep<T>) f.asString().analyzer(KEYWORD_ANALYZER)
        .searchAnalyzer(KEYWORD_ANALYZER);
    }
    return f -> f.as(clazz);
  }

  @SuppressWarnings("unchecked")
  private static <T> Function<? super IndexFieldTypeFactory, ? extends IndexFieldTypeFinalStep<T>> sortAnalyzer(
    Class<T> clazz) {
    if (clazz.equals(String.class)) {
      return f -> (IndexFieldTypeFinalStep<T>) f.asString().sortable(YES)
        .normalizer(SORT_NORMALIZER);
    }
    return f -> f.as(clazz).sortable(YES);
  }


}
