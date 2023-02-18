package muni.fi.cz.jobportal.factory;

import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.api.request.ApplicantCreateDto;
import muni.fi.cz.jobportal.domain.Applicant;
import muni.fi.cz.jobportal.repository.UserRepository;
import org.mapstruct.ObjectFactory;
import org.springframework.stereotype.Component;

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
