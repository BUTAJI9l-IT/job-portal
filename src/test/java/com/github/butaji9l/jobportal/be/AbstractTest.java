package com.github.butaji9l.jobportal.be;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

public abstract class AbstractTest {

  private final ResourceLoader resourceLoader = new DefaultResourceLoader();
  private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

  protected <T> T loadResource(String fileName, Class<T> clazz) {
    try {
      return objectMapper.readValue(
        resourceLoader.getResource("classpath:requests/" + fileName).getInputStream(), clazz);
    } catch (Exception e) {
      throw new RuntimeException("Cannot load a resource");
    }
  }
}
