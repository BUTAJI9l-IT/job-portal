package muni.fi.cz.jobportal.repository;

import muni.fi.cz.jobportal.exception.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Common repository interface
 *
 * @param <T> Type of base repository class.
 * @param <I> Type of id class.
 * @author Vitalii Bortsov
 */
@NoRepositoryBean
public interface JobPortalRepository<T, I> extends JpaRepository<T, I> {

  Class<T> getBaseClass();

  default T getOneByIdOrThrowNotFound(I id) {
    return findById(id).orElseThrow(() -> new EntityNotFoundException(getBaseClass()));
  }
}
