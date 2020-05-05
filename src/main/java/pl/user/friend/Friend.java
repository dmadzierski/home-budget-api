package pl.user.friend;

import lombok.*;
import pl.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "friend")
public class Friend {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "friend_id")
  private Long id;

  @NotNull
  @OneToOne
  private User user;

  @NotNull
  @OneToOne
  private User friend;

  @NotNull
  private LocalDateTime dateOfMakingFiends;

  @Builder
  public Friend (@NotNull User user, @NotNull User friend, @NotNull LocalDateTime dateOfMakingFiends) {
    this.user = user;
    this.friend = friend;
    this.dateOfMakingFiends = dateOfMakingFiends;
  }
}
