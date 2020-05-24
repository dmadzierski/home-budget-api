package pl.wallet;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.user.User;
import pl.wallet.transaction.Transaction;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "wallet")
public class Wallet {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "wallet_id")
  @Id
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private BigDecimal balance;

  @OneToMany
  private List<Transaction> transactions;

  @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  private List<User> users;

  @Transactional
  public void addUser (User user) {
    try {
      users.add(user);
    } catch (NullPointerException e) {
      users = Collections.singletonList(user);
    }
  }

  public void removeUser (User user) {
    users.remove(user);
  }
}
