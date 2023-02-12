package muni.fi.cz.jobportal.service.impl;

import java.io.ByteArrayInputStream;
import muni.fi.cz.jobportal.service.ApplicationService;
import org.springframework.stereotype.Service;

@Service
public class ApplicationServiceImpl implements ApplicationService {

  @Override
  public ByteArrayInputStream generateCV() {
    return null;
  }
}
