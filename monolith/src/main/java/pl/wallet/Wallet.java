package pl.wallet;

import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.user.SimpleUserQueryDto;
import pl.wallet.transaction.SimpleTransactionQueryDto;

import javax.persistence.*;
import java.math.BigDecimal;
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
   private Set<SimpleTransactionQueryDto> transactions;

   @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
   private SimpleUserQueryDto user;

   @Builder(toBuilder = true)
   private Wallet(Long id, String name, BigDecimal balance, Set<SimpleTransactionQueryDto> transactions, SimpleUserQueryDto user) {
      this.id = id;
      this.name = name;
      this.balance = balance;
      this.transactions = transactions;
      this.user = user;
   }

   public void removeTransaction(SimpleTransactionQueryDto transaction) {
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


}
