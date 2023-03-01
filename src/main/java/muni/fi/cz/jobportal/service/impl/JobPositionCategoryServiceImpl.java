package muni.fi.cz.jobportal.service.impl;

import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.annotation.JobPortalService;
import muni.fi.cz.jobportal.api.common.ListOfCategoriesResponse;
import muni.fi.cz.jobportal.api.common.ReferenceDto;
import muni.fi.cz.jobportal.domain.JobCategory;
import muni.fi.cz.jobportal.repository.JobPositionCategoryRepository;
import muni.fi.cz.jobportal.service.JobPositionCategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

@JobPortalService
@RequiredArgsConstructor
public class JobPositionCategoryServiceImpl implements JobPositionCategoryService {

  private final JobPositionCategoryRepository jobPositionCategoryRepository;

  @NonNull
  @Override
  @PreAuthorize("@authorityValidator.isAdmin()")
  public ReferenceDto create(@NonNull String name) {
    final var saved = jobPositionCategoryRepository.save(new JobCategory(null, name));
    return ReferenceDto.builder().id(saved.getId()).name(saved.getName()).build();
  }

  @NonNull
  @Override
  @Transactional(readOnly = true)
  public ListOfCategoriesResponse findAll() {
    return new ListOfCategoriesResponse(jobPositionCategoryRepository.findAll().stream()
      .map(jpc -> ReferenceDto.builder().name(jpc.getName()).id(jpc.getId()).build()).toList());
  }

  @Override
  @PreAuthorize("@authorityValidator.isAdmin()")
  public void delete(@NonNull UUID id) {
    jobPositionCategoryRepository.delete(jobPositionCategoryRepository.getOneByIdOrThrowNotFound(id));
  }
}
