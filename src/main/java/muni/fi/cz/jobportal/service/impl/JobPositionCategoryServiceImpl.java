package muni.fi.cz.jobportal.service.impl;

import java.util.ArrayList;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.annotation.JobPortalService;
import muni.fi.cz.jobportal.api.common.CategoryDto;
import muni.fi.cz.jobportal.api.common.ListOfCategoriesResponse;
import muni.fi.cz.jobportal.api.common.ReferenceDto;
import muni.fi.cz.jobportal.api.search.OccupationQueryParams;
import muni.fi.cz.jobportal.domain.JobCategory;
import muni.fi.cz.jobportal.mapper.CategoryMapper;
import muni.fi.cz.jobportal.repository.JobPositionCategoryRepository;
import muni.fi.cz.jobportal.repository.OccupationRepository;
import muni.fi.cz.jobportal.service.JobPositionCategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link JobPositionCategoryService} Implementation
 *
 * @author Vitalii Bortsov
 */
@JobPortalService
@RequiredArgsConstructor
public class JobPositionCategoryServiceImpl implements JobPositionCategoryService {

  private final JobPositionCategoryRepository jobPositionCategoryRepository;
  private final OccupationRepository occupationRepository;
  private final CategoryMapper categoryMapper;

  @NonNull
  @Override
  @PreAuthorize("@authorityValidator.isAdmin()")
  public ReferenceDto create(@NonNull String name) {
    final var saved = jobPositionCategoryRepository.save(
      new JobCategory(null, name, new ArrayList<>()));
    return ReferenceDto.builder().id(saved.getId()).name(saved.getName()).build();
  }

  @NonNull
  @Override
  @Transactional(readOnly = true)
  public ListOfCategoriesResponse findAll() {
    return new ListOfCategoriesResponse(jobPositionCategoryRepository.findAll().stream()
      .map(jpc -> ReferenceDto.builder().name(jpc.getName()).id(jpc.getId()).build()).toList());
  }

  @NonNull
  @Override
  @Transactional(readOnly = true)
  public Page<ReferenceDto> searchOccupations(@NonNull Pageable pageable,
    @NonNull OccupationQueryParams params) {
    return occupationRepository.search(pageable, params)
      .map(o -> ReferenceDto.builder().id(o.getId()).name(o.getName()).build());
  }

  @Override
  @PreAuthorize("@authorityValidator.isAdmin()")
  public void delete(@NonNull UUID id) {
    jobPositionCategoryRepository.delete(
      jobPositionCategoryRepository.getOneByIdOrThrowNotFound(id));
  }

  @NonNull
  @Override
  @Transactional(readOnly = true)
  public CategoryDto getOccupationsByCategory(@NonNull UUID id) {
    return categoryMapper.map(jobPositionCategoryRepository.getOneByIdOrThrowNotFound(id));
  }
}
