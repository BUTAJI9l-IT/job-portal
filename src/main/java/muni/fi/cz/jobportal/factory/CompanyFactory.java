package muni.fi.cz.jobportal.factory;

import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.api.request.CompanyCreateDto;
import muni.fi.cz.jobportal.domain.Company;
import muni.fi.cz.jobportal.repository.UserRepository;
import org.mapstruct.ObjectFactory;
import org.springframework.stereotype.Component;

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
