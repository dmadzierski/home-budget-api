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
import java.util.ArrayList;
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

  private String ownerEmail;

  @Transactional
  public void addUser (User user) {
    try {
      users.add(user);
    } catch (NullPointerException e) {
      users = new ArrayList<>();
      users.add(user);
    }
  }


  public void removeUser (User user) {
    users.remove(user);
  }

  //  This method only update balance
  public void addTransaction (Transaction transaction) {
    this.balance = transaction.getCategory().getTransactionType().countBalance(this, transaction);
  }

  //  This method only undo changes in balance
  public void removeTransaction (Transaction transaction) {
    this.balance = transaction.getCategory().getTransactionType().undoCountBalance(this, transaction);

  }
}
