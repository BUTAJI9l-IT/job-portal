package muni.fi.cz.jobportal.service;

import java.util.UUID;
import org.springframework.lang.NonNull;

/**
 *  Interface for CRUD operations
 *
 * @param <C> Payload for create operation
 * @param <R> DTO class representing main properties of an entity
 * @param <U> Payload for update operation
 * @param <D> DetailDTO class representing detail properties of an entity
 */
public interface CRUDService<C, R, U, D> {

  @NonNull
  UUID create(@NonNull C payload);

  @NonNull
  D findOne(@NonNull UUID id);

  @NonNull
  R findAll();
  @NonNull
  D update(@NonNull UUID id, @NonNull U payload);

  void delete(@NonNull UUID id);
}
