package pl.wallet.category;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.user.User;
import pl.wallet.transaction.TransactionType;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "category")
public class Category {

  @Column(name = "category_id")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  private String description;

  @Column(nullable = false)
  @Enumerated
  private TransactionType transactionType;

  @ManyToMany
  private List<User> users;

  public void addUser (User user) {
    try {
      users.add(user);
    } catch (NullPointerException e) {
      users = Collections.singletonList(user);
    }
  }
}
