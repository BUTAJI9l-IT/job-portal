package muni.fi.cz.jobportal.service;

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
 */
public interface CRUDService<C, R, U, D, Q> {

  @NonNull
  default D create(@NonNull C payload) {
    throw new UnsupportedOperationException("Create operation is not supported");
  }

  @NonNull
  default D findOne(@NonNull UUID id) {
    throw new UnsupportedOperationException("Get one operation is not supported");
  }

  @NonNull
  default Page<R> findAll(Pageable pageable, Q params) {
    throw new UnsupportedOperationException("Get all operation is not supported");
  }

  @NonNull
  default D update(@NonNull UUID id, @NonNull U payload) {
    throw new UnsupportedOperationException("Update operation is not supported");
  }

  default void delete(@NonNull UUID id) {
    throw new UnsupportedOperationException("Delete operation is not supported");
  }
}
