package pl.wallet;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.user.User;
import pl.wallet.transaction.Transaction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode
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
   private List<Transaction> transactions;

   @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
   private User user;

   public void addTransaction(Transaction transaction) {
      this.balance = transaction.getCategory().getTransactionType().countBalance(this, transaction);
   }

   public void removeTransaction(Transaction transaction) {
      this.balance = transaction.getCategory().getTransactionType().undoCountBalance(this, transaction);

   }

   public Long getId() {
      return id;
   }

   void setId(Long id) {
      this.id = id;
   }

   String getName() {
      return name;
   }

   void setName(String name) {
      this.name = name;
   }

   public BigDecimal getBalance() {
      return balance;
   }

   void setBalance(BigDecimal balance) {
      this.balance = balance;
   }

   List<Transaction> getTransactions() {
      return transactions;
   }

   void setTransactions(List<Transaction> transactions) {
      this.transactions = transactions;
   }

   User getUser() {
      return user;
   }

   void setUser(User user) {
      this.user = user;
   }
}
