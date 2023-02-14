package muni.fi.cz.jobportal.service.impl;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.annotation.JobPortalService;
import muni.fi.cz.jobportal.api.common.UserDto;
import muni.fi.cz.jobportal.api.request.ApplicantCreateDto;
import muni.fi.cz.jobportal.api.request.CompanyCreateDto;
import muni.fi.cz.jobportal.api.request.UserCreateDto;
import muni.fi.cz.jobportal.api.request.UserUpdateDto;
import muni.fi.cz.jobportal.api.search.UserQueryParams;
import muni.fi.cz.jobportal.domain.Applicant;
import muni.fi.cz.jobportal.domain.Company;
import muni.fi.cz.jobportal.enums.JobPortalScope;
import muni.fi.cz.jobportal.exception.EntityNotFoundException;
import muni.fi.cz.jobportal.exception.UserAlreadyRegisteredException;
import muni.fi.cz.jobportal.mapper.UserMapper;
import muni.fi.cz.jobportal.repository.ApplicantRepository;
import muni.fi.cz.jobportal.repository.CompanyRepository;
import muni.fi.cz.jobportal.repository.UserRepository;
import muni.fi.cz.jobportal.service.ApplicantService;
import muni.fi.cz.jobportal.service.CompanyService;
import muni.fi.cz.jobportal.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

@JobPortalService
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final ApplicantRepository applicantRepository;
  private final CompanyRepository companyRepository;
  private final UserMapper userMapper;
  private final ApplicantService applicantService;
  private final CompanyService companyService;

  @NonNull
  @Override
  public UserDto create(@NonNull UserCreateDto payload) {
    if (userRepository.existsByEmail(payload.getEmail())) {
      throw new UserAlreadyRegisteredException(payload.getEmail());
    }
    final var user = userRepository.save(userMapper.map(payload));
    if (payload.getScope().equals(JobPortalScope.REGULAR_USER)) {
      user.setApplicant(
        applicantRepository.findById(applicantService.create(new ApplicantCreateDto(user.getId())).getId())
          .orElseThrow(() -> {
              throw new EntityNotFoundException(Applicant.class);
            }
          )
      );
    } else if (payload.getScope().equals(JobPortalScope.COMPANY)) {
      user.setCompany(
        companyRepository.findById(companyService.create(new CompanyCreateDto(user.getId())).getId())
          .orElseThrow(() -> {
              throw new EntityNotFoundException(Company.class);
            }
          )
      );
    }
    return userMapper.map(user);
  }

  @NonNull
  @Override
  @Transactional(readOnly = true)
  public UserDto findOne(@NonNull UUID id) {
    return UserService.super.findOne(id);
  }

  @NonNull
  @Override
  @Transactional(readOnly = true)
  public Page<UserDto> findAll(Pageable pageable, UserQueryParams params) {
    return UserService.super.findAll(pageable, params);
  }

  @NonNull
  @Override
  public UserDto update(@NonNull UUID id, @NonNull UserUpdateDto payload) {
    return UserService.super.update(id, payload);
  }

  @Override
  public void delete(@NonNull UUID id) {
    UserService.super.delete(id);
  }
}
