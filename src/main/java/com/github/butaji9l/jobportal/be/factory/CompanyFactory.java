package com.github.butaji9l.jobportal.be.factory;

import com.github.butaji9l.jobportal.be.api.request.CompanyCreateDto;
import com.github.butaji9l.jobportal.be.domain.Company;
import com.github.butaji9l.jobportal.be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.ObjectFactory;
import org.springframework.stereotype.Component;

/**
 * Object factory for companies
 *
 * @author Vitalii Bortsov
 */
@Component
@RequiredArgsConstructor
public class CompanyFactory {

  private final UserRepository userRepository;

  @ObjectFactory
  public Company prepare(CompanyCreateDto request) {
    final var company = new Company();
    company.setUser(userRepository.getOneByIdOrThrowNotFound(request.getUser()));
    return company;
  }
}
