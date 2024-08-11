package com.github.butaji9l.jobportal.be.testutils;

import static lombok.AccessLevel.PRIVATE;

import com.github.butaji9l.jobportal.be.domain.Applicant;
import com.github.butaji9l.jobportal.be.domain.Application;
import com.github.butaji9l.jobportal.be.domain.Company;
import com.github.butaji9l.jobportal.be.domain.Experience;
import com.github.butaji9l.jobportal.be.domain.JobCategory;
import com.github.butaji9l.jobportal.be.domain.JobPosition;
import com.github.butaji9l.jobportal.be.domain.Occupation;
import com.github.butaji9l.jobportal.be.domain.RefreshToken;
import com.github.butaji9l.jobportal.be.domain.User;
import com.github.butaji9l.jobportal.be.enums.ApplicationState;
import com.github.butaji9l.jobportal.be.enums.JobPortalScope;
import com.github.butaji9l.jobportal.be.enums.PositionState;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import lombok.NoArgsConstructor;
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
