package muni.fi.cz.jobportal.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "languages")
@EqualsAndHashCode(of = "id")
public class Language {

  @Id
  private String code;
}
