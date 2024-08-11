package com.github.butaji9l.jobportal.be.factory;

import com.github.butaji9l.jobportal.be.api.request.ApplicantCreateDto;
import com.github.butaji9l.jobportal.be.domain.Applicant;
import com.github.butaji9l.jobportal.be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.ObjectFactory;
import org.springframework.stereotype.Component;

/**
 * Object factory for applicants
 *
 * @author Vitalii Bortsov
 */
@Component
@RequiredArgsConstructor
public class ApplicantFactory {

  private final UserRepository userRepository;

  @ObjectFactory
  public Applicant prepare(ApplicantCreateDto request) {
    final var applicant = new Applicant();
    applicant.setUser(userRepository.getOneByIdOrThrowNotFound(request.getUser()));
    return applicant;
  }
}
