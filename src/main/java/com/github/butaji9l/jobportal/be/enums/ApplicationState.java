package com.github.butaji9l.jobportal.be.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ApplicationState {
  OPEN(0),
  SEEN(1),
  APPROVED(2),
  DECLINED(2),
  CLOSED(3);

  @Getter
  private final int state;
}
