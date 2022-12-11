package muni.fi.cz.jobportal.domain;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "job_categories")
@Entity
@EqualsAndHashCode(of = "id")
public class JobCategory {
  @Id
  @GeneratedValue
  private UUID id;

  private String name;
}
