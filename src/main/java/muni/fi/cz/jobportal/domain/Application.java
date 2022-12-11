package muni.fi.cz.jobportal.domain;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import muni.fi.cz.jobportal.enums.ApplicationState;

@Getter
@Setter
@Table(name = "applications")
@Entity
@EqualsAndHashCode(of = "id")
public class Application {

  @Id
  @GeneratedValue
  private UUID id;

  @Enumerated(EnumType.STRING)
  private ApplicationState state;

  private Instant date;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "applicant")
  private Applicant applicant;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "job_position")
  private JobPosition jobPosition;

}
