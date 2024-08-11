package com.github.butaji9l.jobportal.be.utils;

import static com.github.butaji9l.jobportal.be.utils.AuthenticationUtils.getClaim;
import static com.github.butaji9l.jobportal.be.utils.AuthenticationUtils.getJwtFromHeader;

import com.github.butaji9l.jobportal.be.api.JwtClaims;
import com.github.butaji9l.jobportal.be.api.request.JobPositionCreateDto;
import com.github.butaji9l.jobportal.be.enums.JobPortalScope;
import com.github.butaji9l.jobportal.be.repository.ApplicantRepository;
import com.github.butaji9l.jobportal.be.repository.ApplicationRepository;
import com.github.butaji9l.jobportal.be.repository.CompanyRepository;
import com.github.butaji9l.jobportal.be.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Authority validator class contains methods for validating permissions of current authorized user
 * to perform certain operations in the system.
 *
 * @author Vitalii Bortsov
 */
@Component("authorityValidator")
@RequiredArgsConstructor
@Transactional
public class AuthorityValidator {

  private final CompanyRepository companyRepository;
  private final UserRepository userRepository;
  private final JwtDecoder jwtDecoder;
  private final ApplicantRepository applicantRepository;
  private final ApplicationRepository applicationRepository;

  public boolean jobBelongsToCompany(UUID id) {
    return isAdmin() || (isCompany() && companyRepository.userWithJobExists(getCurrentUser(), id));
  }

  public UUID getCurrentUserFromHeader() {
    final var token = getJwtFromHeader();
    return token == null ? null : UUID.fromString(jwtDecoder.decode(token).getSubject());
  }

  public UUID getCurrentUser() {
    return AuthenticationUtils.getCurrentUser();
  }

  public boolean canCreateJobPosition(@NonNull JobPositionCreateDto payload) {
    return isAdmin() || (isCompany() && isCurrentUser(
      companyRepository.getOneByIdOrThrowNotFound(payload.getCompany()).getUser().getId()));
  }

  public boolean canManageApplication(@NonNull UUID id) {
    return isAdmin() || (isCompany() && isCurrentUser(
      applicationRepository.getOneByIdOrThrowNotFound(id).getJobPosition().getCompany().getUser()
        .getId()));
  }

  public boolean canDeleteApplication(@NonNull UUID id) {
    return isAdmin() || (isRegularUser() && isCurrentUser(
      applicationRepository.getOneByIdOrThrowNotFound(id).getApplicant().getUser().getId()));
  }

  private static boolean hasScope(String scope) {
    return getClaim(JwtClaims.SCOPE_CLAIM).map(s -> s.equals(scope)).orElse(false);
  }

  public boolean isCurrentUser(UUID user) {
    return getCurrentUser().equals(user);
  }

  public boolean isCurrentApplicant(UUID applicant) {
    return isRegularUser() && isCurrentUser(getUserFromApplicant(applicant));
  }

  public boolean isCurrentCompany(UUID company) {
    return isCompany() && isCurrentUser(getUserFromCompany(company));
  }

  public boolean isAdmin() {
    return hasScope(JobPortalScope.ADMIN.toString()) && userRepository.isAdmin(getCurrentUser());
  }

  public boolean isRegularUser() {
    return hasScope(JobPortalScope.REGULAR_USER.toString()) && userRepository.isRegularUser(
      getCurrentUser());
  }

  public boolean isCompany() {
    return hasScope(JobPortalScope.COMPANY.toString()) && userRepository.isCompany(
      getCurrentUser());
  }

  private UUID getUserFromApplicant(UUID id) {
    return applicantRepository.getOneByIdOrThrowNotFound(id).getUser().getId();
  }

  private UUID getUserFromCompany(UUID id) {
    return companyRepository.getOneByIdOrThrowNotFound(id).getUser().getId();
  }
}
