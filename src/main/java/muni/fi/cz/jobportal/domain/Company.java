package muni.fi.cz.jobportal.domain;

import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import muni.fi.cz.jobportal.enums.CompanyNumberOfEmployees;

@Getter
@Setter
@Table(name = "companies")
@Entity
@EqualsAndHashCode(of = "id")
public class Company {

  @Id
  @GeneratedValue
  private UUID id;
  private String companyName;
  private String companyLink;
  private String description;

  @Enumerated(EnumType.STRING)
  private CompanyNumberOfEmployees companySize;

  @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
  private List<JobPosition> jobPositions;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;
}
