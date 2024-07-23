package muni.fi.cz.jobportal.repository.search.impl;

import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.SORT_SUFFIX;
import static muni.fi.cz.jobportal.utils.ClassFieldsUtils.applyToAnnotatedFieldsWithValuesPresent;
import static muni.fi.cz.jobportal.utils.ClassFieldsUtils.findAnnotation;
import static muni.fi.cz.jobportal.utils.ClassFieldsUtils.getFieldValue;
import static muni.fi.cz.jobportal.utils.ClassFieldsUtils.isCollectionType;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.lang.reflect.Field;
import java.time.temporal.TemporalAdjuster;
import java.util.Collection;
import java.util.function.Consumer;
import muni.fi.cz.jobportal.annotation.search.DateQueryField;
import muni.fi.cz.jobportal.annotation.search.DateQueryField.RangeSide;
import muni.fi.cz.jobportal.annotation.search.KeywordQueryField;
import muni.fi.cz.jobportal.api.search.QueryParams;
import muni.fi.cz.jobportal.repository.search.SearchRepository;
import org.hibernate.SessionFactory;
import org.hibernate.search.engine.search.predicate.SearchPredicate;
import org.hibernate.search.engine.search.predicate.dsl.BooleanPredicateClausesStep;
import org.hibernate.search.engine.search.predicate.dsl.SearchPredicateFactory;
import org.hibernate.search.engine.search.sort.dsl.SortOrder;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.scope.SearchScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;

/**
 * {@link SearchRepository} abstract implementation class.
 *
 * @param <T> Type of base repository class.
 * @param <Q> Type of search parameters class.
 * @author Vitalii Bortsov
 */
public abstract class AbstractJobPortalSearchRepository<T, Q extends QueryParams> implements
  SearchRepository<T, Q> {

  @Autowired
  private EntityManager entityManager;
  @Autowired
  private EntityManagerFactory entityManagerFactory;

  @Override
  public void afterPropertiesSet() {
    Search.session(entityManagerFactory.unwrap(SessionFactory.class).openSession())
      .massIndexer(getBaseClass()).start();
  }

  @Override
  public Page<T> search(Pageable pageable, Q params) {
    final var searchSession = Search.session(entityManager);
    final var scope = searchSession.scope(getBaseClass());
    var luceneQuery = searchSession.search(scope)
      .where(getPredicate(params, scope, scope.predicate().bool()));
    if (pageable.getSort() != Sort.unsorted()) {
      luceneQuery = luceneQuery.sort(sort ->
        sort.composite(c ->
          pageable.getSort().forEach(o ->
            c.add(sort.field(o.getProperty() + SORT_SUFFIX).order(sort(o))))
        )
      );
    }
    final var result = luceneQuery.fetch((int) pageable.getOffset(), pageable.getPageSize());
    return new PageImpl<>(result.hits(), pageable, result.total().hitCount());
  }

  private SearchPredicate getPredicate(Q params, SearchScope<T> scope,
    BooleanPredicateClausesStep<?> rootPredicate) {
    var isNotEmpty = addFulltext(params, scope, rootPredicate);
    isNotEmpty |= addKeywords(params, scope, rootPredicate);
    if (!isNotEmpty) {
      rootPredicate.must(SearchPredicateFactory::matchAll);
    }
    return rootPredicate.toPredicate();
  }

  private boolean addFulltext(Q params, SearchScope<T> scope,
    BooleanPredicateClausesStep<?> rootPredicate) {
    if (!CollectionUtils.isEmpty(params.getQList()) && params.getQueryIndices().length != 0) {
      final var colPredicate = scope.predicate().bool();
      params.getQList().forEach(
        colValue -> colPredicate.should(
          scope.predicate().match().fields(params.getQueryIndices()).matching(colValue)));
      rootPredicate.must(colPredicate);
      return true;
    }
    return false;
  }

  private boolean addKeywords(@NonNull Q queryParams, @NonNull SearchScope<T> scope,
    @NonNull BooleanPredicateClausesStep<?> rootPredicate) {
    return applyToAnnotatedFieldsWithValuesPresent(queryParams, KeywordQueryField.class,
      processQueryField(queryParams, scope, rootPredicate));
  }

  private Consumer<Field> processQueryField(Q queryParams, SearchScope<T> scope,
    BooleanPredicateClausesStep<?> rootPredicate) {
    return field -> {
      final var value = getFieldValue(field, queryParams);
      final var annotation = findAnnotation(field, KeywordQueryField.class);
      final var indexField = annotation.value();
      final var dateAnnotation = findAnnotation(field, DateQueryField.class);
      if (dateAnnotation != null && value instanceof TemporalAdjuster dateTime) {
        if (dateAnnotation.side().equals(RangeSide.FROM)) {
          rootPredicate.must(scope.predicate().range().field(indexField).atLeast(dateTime));
        } else {
          rootPredicate.must(scope.predicate().range().field(indexField).atMost(dateTime));
        }
      } else if (isCollectionType(field) && value instanceof Collection<?> collection) {
        final var colPredicate = scope.predicate().bool();
        collection.forEach(
          colValue -> colPredicate.should(
            scope.predicate().match().field(indexField).matching(colValue.toString())));
        rootPredicate.must(colPredicate);
      } else {
        rootPredicate.must(
          scope.predicate().match().field(indexField)
            .matching(annotation.generic() ? value : value.toString()));
      }
    };
  }

  private SortOrder sort(Sort.Order direction) {
    return direction.getDirection() == Direction.ASC ? SortOrder.ASC : SortOrder.DESC;
  }

}
