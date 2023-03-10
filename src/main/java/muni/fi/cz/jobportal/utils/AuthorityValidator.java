package muni.fi.cz.jobportal.utils;

import static muni.fi.cz.jobportal.api.JwtClaims.SCOPE_CLAIM;
import static muni.fi.cz.jobportal.enums.JobPortalScope.ADMIN;
import static muni.fi.cz.jobportal.enums.JobPortalScope.COMPANY;
import static muni.fi.cz.jobportal.enums.JobPortalScope.REGULAR_USER;
import static muni.fi.cz.jobportal.utils.AuthenticationUtils.getClaim;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.api.request.JobPositionCreateDto;
import muni.fi.cz.jobportal.repository.ApplicantRepository;
import muni.fi.cz.jobportal.repository.ApplicationRepository;
import muni.fi.cz.jobportal.repository.CompanyRepository;
import muni.fi.cz.jobportal.repository.UserRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Authority validator class contains methods for validating permissions of current authorized user to perform certain
 * operations in the system.
 *
 * @author Vitalii Bortsov
 */
@Component("authorityValidator")
@RequiredArgsConstructor
@Transactional
public class AuthorityValidator {

  private final CompanyRepository companyRepository;
  private final UserRepository userRepository;
  private final ApplicantRepository applicantRepository;
  private final ApplicationRepository applicationRepository;

  public boolean jobBelongsToCompany(UUID id) {
    return isAdmin() || (isCompany() && companyRepository.userWithJobExists(getCurrentUser(), id));
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
      applicationRepository.getOneByIdOrThrowNotFound(id).getJobPosition().getCompany().getUser().getId()));
  }

  public boolean canDeleteApplication(@NonNull UUID id) {
    return isAdmin() || (isRegularUser() && isCurrentUser(getUserFromApplicant(id)));
  }

  private static boolean hasScope(String scope) {
    return getClaim(SCOPE_CLAIM).map(s -> s.equals(scope)).orElse(false);
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
    return hasScope(ADMIN.toString()) && userRepository.isAdmin(getCurrentUser());
  }

  public boolean isRegularUser() {
    return hasScope(REGULAR_USER.toString()) && userRepository.isRegularUser(getCurrentUser());
  }

  public boolean isCompany() {
    return hasScope(COMPANY.toString()) && userRepository.isCompany(getCurrentUser());
  }

  private UUID getUserFromApplicant(UUID id) {
    return applicantRepository.getOneByIdOrThrowNotFound(id).getUser().getId();
  }

  private UUID getUserFromCompany(UUID id) {
    return companyRepository.getOneByIdOrThrowNotFound(id).getUser().getId();
  }
}
