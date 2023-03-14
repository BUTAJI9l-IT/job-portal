package muni.fi.cz.jobportal.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "languages")
@EqualsAndHashCode(of = "id")
public class Language {

  @Id
  private String code;
}
