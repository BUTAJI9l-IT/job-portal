package muni.fi.cz.jobportal.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

/**
 * Job Category entity class.
 *
 * @author Vitalii Bortsov
 */
@Getter
@Setter
@NoArgsConstructor
@Table(name = "job_categories")
@Entity
@EqualsAndHashCode(of = "id")
public class JobCategory {

  public JobCategory(UUID id, String name, List<Occupation> occupations) {
    this.id = id;
    this.name = name;
    this.occupations = occupations;
  }

  @Id
  @GeneratedValue
  private UUID id;

  private String name;

  @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
  private List<Occupation> occupations;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "position_category",
    joinColumns = @JoinColumn(name = "category", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "job_position", referencedColumnName = "id"))
  private List<JobPosition> positions;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "experience_category",
    joinColumns = @JoinColumn(name = "category", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "experience", referencedColumnName = "id"))
  private List<JobCategory> experiences;
}
