package com.github.butaji9l.jobportal.be.service.impl;

import com.github.butaji9l.jobportal.be.annotation.JobPortalService;
import com.github.butaji9l.jobportal.be.api.common.CategoryDto;
import com.github.butaji9l.jobportal.be.api.common.ListOfCategoriesResponse;
import com.github.butaji9l.jobportal.be.api.common.ReferenceDto;
import com.github.butaji9l.jobportal.be.api.search.OccupationQueryParams;
import com.github.butaji9l.jobportal.be.domain.JobCategory;
import com.github.butaji9l.jobportal.be.mapper.CategoryMapper;
import com.github.butaji9l.jobportal.be.repository.JobPositionCategoryRepository;
import com.github.butaji9l.jobportal.be.repository.OccupationRepository;
import com.github.butaji9l.jobportal.be.service.JobPositionCategoryService;
import java.util.ArrayList;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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
