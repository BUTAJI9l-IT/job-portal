package com.github.butaji9l.jobportal.be.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CompanyNumberOfEmployees {
  MICRO(1, 49),
  SMALL(50, 149),
  MEDIUM(150, 499),
  LARGE(500, 999),
  CORPORATION(1000, null);

  private final Integer minEmployees;
  private final Integer maxEmployees;

}
