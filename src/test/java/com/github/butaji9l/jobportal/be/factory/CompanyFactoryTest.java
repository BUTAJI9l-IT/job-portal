package com.github.butaji9l.jobportal.be.factory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.github.butaji9l.jobportal.be.AbstractTest;
import com.github.butaji9l.jobportal.be.api.request.CompanyCreateDto;
import com.github.butaji9l.jobportal.be.domain.User;
import com.github.butaji9l.jobportal.be.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CompanyFactoryTest extends AbstractTest {

  @Mock
  private UserRepository userRepository;
  @InjectMocks
  private CompanyFactory companyFactory;

  @Test
  void prepareCompanyTest() {
    final var request = loadResource("company_create_request.json", CompanyCreateDto.class);

    when(userRepository.getOneByIdOrThrowNotFound(any())).thenReturn(new User());

    final var result = companyFactory.prepare(request);
    assertThat(result.getUser()).isNotNull();
  }
}
