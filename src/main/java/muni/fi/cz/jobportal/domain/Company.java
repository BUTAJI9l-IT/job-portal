package muni.fi.cz.jobportal.domain;

import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "companies")
@Entity
@EqualsAndHashCode(of = "id")
public class Company {

  @Id
  @GeneratedValue
  private UUID id;

  // unique, need to be set when register as company
  private String cin;

  private String companyName;

  private String companyLink;

  @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
  private List<JobPosition> jobPositions;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;
}
