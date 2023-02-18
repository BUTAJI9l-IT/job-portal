package muni.fi.cz.jobportal.domain;

import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import muni.fi.cz.jobportal.enums.JobPortalScope;

@Getter
@Setter
@Table(name = "jp_users")
@Entity
@EqualsAndHashCode(of = "id")
public class User {

  @Id
  @GeneratedValue
  private UUID id;

  @Column(name = "email", unique = true)
  private String email;
  @Column(name = "password")
  private String password;

  private String name;
  private String lastName;

  @Enumerated(EnumType.STRING)
  @Column(name = "scope")
  private JobPortalScope scope;

  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
  @JoinColumn(name = "applicant_id")
  private Applicant applicant;

  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
  @JoinColumn(name = "company_id")
  private Company company;

  public String getFullName() {
    if (name == null) {
      return lastName == null ? "" : lastName;
    }
    return lastName == null ? name : name + " " + lastName;
  }

}
