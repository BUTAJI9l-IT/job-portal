package com.github.butaji9l.jobportal.be.service;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

/**
 * Interface for CRUD operations
 *
 * @param <C> Payload for create operation
 * @param <R> DTO class representing main properties of an entity
 * @param <U> Payload for update operation
 * @param <D> DetailDTO class representing detail properties of an entity
 * @author Vitalii Bortsov
 */
public interface CRUDService<C, R, U, D, Q> {

  /**
   * Method creates an entity in datasource and returns the saved data.
   *
   * @param payload create dto for this entity
   * @return detail dto for this entity
   */
  @NonNull
  default D create(@NonNull C payload) {
    throw new UnsupportedOperationException("Create operation is not supported");
  }

  /**
   * Method gets from datasource an entity by given id.
   *
   * @param id id of the entity to be returned
   * @return detail dto for this entity
   */
  @NonNull
  default D findOne(@NonNull UUID id) {
    throw new UnsupportedOperationException("Get one operation is not supported");
  }

  /**
   * Method finds entities by input params.
   *
   * @param pageable pageable parameter
   * @param params   search parameters with filters and fulltext search query
   * @return Pageable model with main properties of entities, which have matched query params
   */
  @NonNull
  default Page<R> findAll(Pageable pageable, Q params) {
    throw new UnsupportedOperationException("Get all operation is not supported");
  }

  /**
   * Method updates an entity by given id and payload in datasource.
   *
   * @param id      id of the entity to be updated
   * @param payload update payload
   * @return detail dto for this entity
   */
  @NonNull
  default D update(@NonNull UUID id, @NonNull U payload) {
    throw new UnsupportedOperationException("Update operation is not supported");
  }

  /**
   * Deletes an entity by given id in datasource if exists.
   *
   * @param id id of the entity to be deleted
   */
  default void delete(@NonNull UUID id) {
    throw new UnsupportedOperationException("Delete operation is not supported");
  }
}
