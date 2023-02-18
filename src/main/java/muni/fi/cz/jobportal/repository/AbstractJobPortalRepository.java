package muni.fi.cz.jobportal.repository;

import muni.fi.cz.jobportal.exception.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractJobPortalRepository<T, I> extends JpaRepository<T, I> {

  Class<T> getBaseClass();

  default T getOneByIdOrThrowNotFound(I id) {
    return findById(id).orElseThrow(() -> {
      throw new EntityNotFoundException(getBaseClass());
    });
  }
}
