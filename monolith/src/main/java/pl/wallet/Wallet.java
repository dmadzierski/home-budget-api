package pl.wallet;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.user.User;
import pl.wallet.transaction.Transaction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

@NoArgsConstructor
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

   @OneToMany(mappedBy = "wallet")
   private Set<Transaction> transactions;

   @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
   private User user;

   @Builder(toBuilder = true)
   private Wallet(Long id, String name, BigDecimal balance, Set<Transaction> transactions, User user) {
      this.id = id;
      this.name = name;
      this.balance = balance;
      this.transactions = transactions;
      this.user = user;
   }

   public void addTransaction(Transaction transaction) {
      this.balance = transaction.getCategory().getTransactionType().countBalance(this, transaction);
   }

   public void removeTransaction(Transaction transaction) {
      this.balance = transaction.getCategory().getTransactionType().undoCountBalance(this, transaction);

   }

   public Long getId() {
      return id;
   }


   String getName() {
      return name;
   }


   public BigDecimal getBalance() {
      return balance;
   }


   Set<Transaction> getTransactions() {
      return Collections.unmodifiableSet(transactions);
   }


   User getUser() {
      return user;
   }

}
