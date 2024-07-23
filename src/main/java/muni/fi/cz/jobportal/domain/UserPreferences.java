package muni.fi.cz.jobportal.domain;

import com.neovisionaries.i18n.LanguageCode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "preferences")
@EqualsAndHashCode(of = "id")
public class UserPreferences {

  @Id
  @GeneratedValue
  private UUID id;

  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  private Boolean notificationsEnabled;
  @Column(name = "lang")
  @Enumerated(value = EnumType.STRING)
  private LanguageCode language = LanguageCode.en;
}
