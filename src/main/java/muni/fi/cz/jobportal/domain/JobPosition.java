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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import muni.fi.cz.jobportal.enums.PositionState;

@Getter
@Setter
@Table(name = "job_positions")
@Entity
@EqualsAndHashCode(of = "id")
public class JobPosition {

  @Id
  @GeneratedValue
  private UUID id;
  @Enumerated(EnumType.STRING)
  private PositionState status;
  private String positionName;

  private String country;
  private String state;
  private String city;

  private String contactEmail;
  private String detail;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "company_id")
  private Company company;

  @OneToMany(mappedBy = "jobPosition", fetch = FetchType.LAZY)
  private List<Application> applications;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "position_category",
    joinColumns = @JoinColumn(name = "job_position", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "category", referencedColumnName = "id"))
  private List<JobCategory> jobCategories;
}
