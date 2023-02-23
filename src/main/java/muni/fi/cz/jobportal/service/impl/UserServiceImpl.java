package muni.fi.cz.jobportal.service.impl;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.annotation.JobPortalService;
import muni.fi.cz.jobportal.api.common.UserDto;
import muni.fi.cz.jobportal.api.request.ApplicantCreateDto;
import muni.fi.cz.jobportal.api.request.UserCreateDto;
import muni.fi.cz.jobportal.api.request.UserUpdateDto;
import muni.fi.cz.jobportal.enums.JobPortalScope;
import muni.fi.cz.jobportal.exception.OldPasswordMismatchException;
import muni.fi.cz.jobportal.exception.UserAlreadyRegisteredException;
import muni.fi.cz.jobportal.mapper.CompanyMapper;
import muni.fi.cz.jobportal.mapper.UserMapper;
import muni.fi.cz.jobportal.repository.ApplicantRepository;
import muni.fi.cz.jobportal.repository.CompanyRepository;
import muni.fi.cz.jobportal.repository.UserRepository;
import muni.fi.cz.jobportal.service.ApplicantService;
import muni.fi.cz.jobportal.service.CompanyService;
import muni.fi.cz.jobportal.service.UserService;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@JobPortalService
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final ApplicantRepository applicantRepository;
  private final CompanyRepository companyRepository;
  private final UserMapper userMapper;
  private final CompanyMapper companyMapper;
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
        companyRepository.getOneByIdOrThrowNotFound(companyService.create(companyMapper.map(payload, user)).getId()));
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
    if (!passwordEncoder.matches(user.getPassword(), payload.getOldPassword())) {
      throw new OldPasswordMismatchException();
    }
    return userMapper.map(userRepository.saveAndFlush(user));
  }

  @Override
  @PreAuthorize("@authorityValidator.isAdmin() || @authorityValidator.isCurrentUser(#id)")
  public void delete(@NonNull UUID id) {
    userRepository.delete(userRepository.getOneByIdOrThrowNotFound(id));
  }
}
