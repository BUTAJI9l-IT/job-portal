package muni.fi.cz.jobportal.domain;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

/**
 * File entity class.
 *
 * @author Vitalii Bortsov
 */
@Getter
@Setter
@Table(name = "files")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class File {

  @Id
  @GeneratedValue
  private UUID id;

  @Column(name = "file_name")
  private String name;
  private String ext;
  @Lob
  private byte[] data;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "uploaded_by")
  private User uploadedBy;
}
