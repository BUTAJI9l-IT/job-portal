package muni.fi.cz.jobportal.testutils;

import static lombok.AccessLevel.PRIVATE;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import lombok.NoArgsConstructor;
import muni.fi.cz.jobportal.domain.Applicant;
import muni.fi.cz.jobportal.domain.Application;
import muni.fi.cz.jobportal.domain.Company;
import muni.fi.cz.jobportal.domain.Experience;
import muni.fi.cz.jobportal.domain.JobCategory;
import muni.fi.cz.jobportal.domain.JobPosition;
import muni.fi.cz.jobportal.domain.Occupation;
import muni.fi.cz.jobportal.domain.RefreshToken;
import muni.fi.cz.jobportal.domain.User;
import muni.fi.cz.jobportal.enums.ApplicationState;
import muni.fi.cz.jobportal.enums.JobPortalScope;
import muni.fi.cz.jobportal.enums.PositionState;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor(access = PRIVATE)
public class EntityUtils {

  public static User prepareApplicantEntity(String email) {
    final var applicant = new Applicant();
    final User user = prepareUserEntity(email, JobPortalScope.REGULAR_USER);
    applicant.setUser(user);
    user.setApplicant(applicant);
    return user;
  }

  @NotNull
  public static User prepareUserEntity(String email, JobPortalScope scope) {
    final var user = new User();
    user.setPassword("1234");
    user.setEmail(email);
    user.setScope(scope);
    return user;
  }

  public static User prepareCompanyEntity(String name, String email) {
    final var company = new Company();
    final User user = prepareUserEntity(email, JobPortalScope.COMPANY);
    company.setUser(user);
    user.setCompany(company);
    company.setCompanyName(name);
    return user;
  }

  public static JobPosition preparePositionEntity(Company company, PositionState status) {
    final var jobPosition = new JobPosition();
    jobPosition.setPositionName("dummy");
    jobPosition.setDetail("dummy");
    jobPosition.setCreated(Instant.now());
    jobPosition.setLastUpdated(Instant.now());
    jobPosition.setStatus(status);
    jobPosition.setCompany(company);
    return jobPosition;
  }

  public static Application prepareApplicationEntity(Applicant applicant, JobPosition jobPosition,
      ApplicationState status) {
    final var application = new Application();
    application.setApplicant(applicant);
    application.setJobPosition(jobPosition);
    application.setState(status);
    application.setDate(Instant.now());
    return application;
  }

  public static Occupation prepareOccupationEntity(JobCategory category, String name) {
    final var occupation = new Occupation();
    occupation.setName(name);
    occupation.setCategory(category);
    return occupation;
  }

  public static JobCategory prepareCategoryEntity(String name) {
    final var category = new JobCategory();
    category.setName(name);
    return category;
  }

  public static RefreshToken prepareRefreshTokenEntity(User user, Instant expires) {
    final var token = new RefreshToken();
    token.setToken(UUID.randomUUID().toString());
    token.setExpires(expires);
    token.setUser(user);
    return token;
  }

  public static Experience prepareExperienceEntity(Applicant applicant) {
    final var experience = new Experience();
    experience.setApplicant(applicant);
    experience.setCompanyName("some company");
    experience.setOccupation("proffesion");
    experience.setFromDate(LocalDate.now());
    return experience;
  }
}
