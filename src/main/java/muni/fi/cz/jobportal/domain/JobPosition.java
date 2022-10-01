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
@Table(name = "positions")
@Entity
@EqualsAndHashCode(of = "id")
public class JobPosition {

  @Id
  @GeneratedValue
  private UUID id;

}
