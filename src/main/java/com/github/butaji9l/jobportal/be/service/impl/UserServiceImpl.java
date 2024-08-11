package com.github.butaji9l.jobportal.be.service.impl;

import com.github.butaji9l.jobportal.be.annotation.JobPortalService;
import com.github.butaji9l.jobportal.be.api.common.PreferencesDto;
import com.github.butaji9l.jobportal.be.api.common.UserDto;
import com.github.butaji9l.jobportal.be.api.request.ApplicantCreateDto;
import com.github.butaji9l.jobportal.be.api.request.UserCreateDto;
import com.github.butaji9l.jobportal.be.api.request.UserUpdateDto;
import com.github.butaji9l.jobportal.be.api.search.UserQueryParams;
import com.github.butaji9l.jobportal.be.enums.JobPortalScope;
import com.github.butaji9l.jobportal.be.exception.OldPasswordMismatchException;
import com.github.butaji9l.jobportal.be.exception.UserAlreadyRegisteredException;
import com.github.butaji9l.jobportal.be.mapper.CompanyMapper;
import com.github.butaji9l.jobportal.be.mapper.PreferencesMapper;
import com.github.butaji9l.jobportal.be.mapper.UserMapper;
import com.github.butaji9l.jobportal.be.repository.ApplicantRepository;
import com.github.butaji9l.jobportal.be.repository.CompanyRepository;
import com.github.butaji9l.jobportal.be.repository.ExperienceRepository;
import com.github.butaji9l.jobportal.be.repository.PreferencesRepository;
import com.github.butaji9l.jobportal.be.repository.RefreshTokenRepository;
import com.github.butaji9l.jobportal.be.repository.UserRepository;
import com.github.butaji9l.jobportal.be.service.ApplicantService;
import com.github.butaji9l.jobportal.be.service.CompanyService;
import com.github.butaji9l.jobportal.be.service.UserService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link UserService} Implementation
 *
 * @author Vitalii Bortsov
 */
@JobPortalService
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final ApplicantRepository applicantRepository;
  private final ExperienceRepository experienceRepository;
  private final CompanyRepository companyRepository;
  private final RefreshTokenRepository refreshTokenRepository;
  private final PreferencesRepository preferencesRepository;
  private final UserMapper userMapper;
  private final CompanyMapper companyMapper;
  private final PreferencesMapper preferencesMapper;
  private final ApplicantService applicantService;
  private final CompanyService companyService;
  private final PasswordEncoder passwordEncoder;


  @NonNull
  @Override
  public UserDto create(@NonNull UserCreateDto payload) {
    if (userRepository.existsByEmail(payload.getEmail())) {
      throw new UserAlreadyRegisteredException(payload.getEmail());
    }
    final var user = userRepository.save(userMapper.map(payload));
    if (payload.getScope().equals(JobPortalScope.REGULAR_USER)) {
      user.setApplicant(applicantRepository.getOneByIdOrThrowNotFound(
        applicantService.create(new ApplicantCreateDto(user.getId())).getId()));
    } else if (payload.getScope().equals(JobPortalScope.COMPANY)) {
      user.setCompany(
        companyRepository.getOneByIdOrThrowNotFound(
          companyService.create(companyMapper.map(payload, user)).getId()));
    }
    return userMapper.map(user);
  }

  @NonNull
  @Override
  @Transactional(readOnly = true)
  @PreAuthorize("@authorityValidator.isAdmin() || @authorityValidator.isCurrentUser(#id)")
  public UserDto findOne(@NonNull UUID id) {
    return userMapper.map(userRepository.getOneByIdOrThrowNotFound(id));
  }

  @NonNull
  @Override
  @PreAuthorize("@authorityValidator.isAdmin() || @authorityValidator.isCurrentUser(#id)")
  public UserDto update(@NonNull UUID id, @NonNull UserUpdateDto payload) {
    final var user = userRepository.getOneByIdOrThrowNotFound(id);
    if (!passwordEncoder.matches(payload.getOldPassword(), user.getPassword())) {
      throw new OldPasswordMismatchException();
    }
    user.setPassword(passwordEncoder.encode(payload.getPassword().getPassword()));
    return userMapper.map(userRepository.saveAndFlush(user));
  }

  @Override
  @PreAuthorize("@authorityValidator.isAdmin() || @authorityValidator.isCurrentUser(#id)")
  public void delete(@NonNull UUID id) {
    final var user = userRepository.getOneByIdOrThrowNotFound(id);
    refreshTokenRepository.deleteAllByUserId(id);
    if (user.getCompany() != null) {
      experienceRepository.findExperiencesByCompany(user.getCompany().getId())
        .forEach(experience -> {
          experience.setCompany(null);
          experienceRepository.save(experience);
        });
    }
    userRepository.delete(user);
  }

  @NonNull
  @Override
  @Transactional(readOnly = true)
  @PreAuthorize("@authorityValidator.isAdmin()")
  public Page<UserDto> findAll(@NonNull Pageable pageable, @NonNull UserQueryParams params) {
    return userRepository.search(pageable, params).map(userMapper::map);
  }

  @NonNull
  @Override
  @PreAuthorize("@authorityValidator.isAdmin() || @authorityValidator.isCurrentUser(#userId)")
  public PreferencesDto updatePreferences(@NonNull UUID userId, @NonNull PreferencesDto payload) {
    final var userPreferences = userRepository.getOneByIdOrThrowNotFound(userId).getPreferences();
    preferencesMapper.update(userPreferences, payload);
    return preferencesMapper.map(preferencesRepository.save(userPreferences));
  }

  @NonNull
  @Override
  @PreAuthorize("@authorityValidator.isAdmin() || @authorityValidator.isCurrentUser(#userId)")
  public PreferencesDto getUserPreferences(@NonNull UUID userId) {
    return preferencesMapper.map(userRepository.getOneByIdOrThrowNotFound(userId).getPreferences());
  }
}
