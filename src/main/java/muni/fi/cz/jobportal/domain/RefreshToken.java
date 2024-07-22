package muni.fi.cz.jobportal.domain;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

/**
 * Refresh Token entity class.
 *
 * @author Vitalii Bortsov
 */
@EqualsAndHashCode(of = "token")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

  @Id
  private String token;

  private Instant expires;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "jp_user")
  private User user;
}
