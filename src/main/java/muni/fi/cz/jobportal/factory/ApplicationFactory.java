package muni.fi.cz.jobportal.factory;

import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.api.request.ApplicationCreateDto;
import muni.fi.cz.jobportal.domain.Application;
import muni.fi.cz.jobportal.repository.ApplicantRepository;
import muni.fi.cz.jobportal.repository.JobPositionRepository;
import muni.fi.cz.jobportal.utils.StaticObjectFactory;
import org.mapstruct.ObjectFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationFactory {

  private final StaticObjectFactory staticObjectFactory;
  private final JobPositionRepository jobPositionRepository;
  private final ApplicantRepository applicantRepository;

  @ObjectFactory
  public Application prepare(ApplicationCreateDto payload) {
    final var application = new Application();
    application.setApplicant(applicantRepository.getOneByIdOrThrowNotFound(payload.getApplicant()));
    application.setJobPosition(jobPositionRepository.getOneByIdOrThrowNotFound(payload.getJob()));
    application.setDate(staticObjectFactory.now());
    return application;
  }
}
