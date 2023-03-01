package muni.fi.cz.jobportal.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "experiences")
@Entity
@EqualsAndHashCode(of = "id")
public class Experience {

  @Id
  @GeneratedValue
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "company_id")
  private Company company;
  private String companyName;
  private String occupation;

  private LocalDate fromDate;
  private LocalDate toDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "applicant_id")
  private Applicant applicant;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "experience_category",
    joinColumns = @JoinColumn(name = "experience", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "category", referencedColumnName = "id"))
  private List<JobCategory> jobCategories;

}
