package com.github.butaji9l.jobportal.be.factory;

import com.github.butaji9l.jobportal.be.api.request.ApplicationCreateDto;
import com.github.butaji9l.jobportal.be.domain.Application;
import com.github.butaji9l.jobportal.be.repository.ApplicantRepository;
import com.github.butaji9l.jobportal.be.repository.JobPositionRepository;
import com.github.butaji9l.jobportal.be.utils.StaticObjectFactory;
import lombok.RequiredArgsConstructor;
import org.mapstruct.ObjectFactory;
import org.springframework.stereotype.Component;

/**
 * Object factory for applications
 *
 * @author Vitalii Bortsov
 */
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
