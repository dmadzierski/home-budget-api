package pl.wallet.transaction;

import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.wallet.SimpleWalletQueryDto;
import pl.wallet.category.SimpleCategoryQueryDto;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Table(name = "transaction")
class Transaction {

   @Column(name = "transaction_id")
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false)
   private String name;

   private String description;

   @OneToOne
   private SimpleCategoryQueryDto category;

   @Column(nullable = false)
   private BigDecimal price;

   @ManyToOne
   private SimpleWalletQueryDto wallet;

   private LocalDateTime dateOfPurchase;

   private Boolean isFinished;

   private Long transactionIdReference;

   @Builder(toBuilder = true)
   private Transaction(String name, String description, SimpleCategoryQueryDto category, BigDecimal price, SimpleWalletQueryDto wallet, LocalDateTime dateOfPurchase, Boolean isFinished, Long transactionIdReference) {
      this.name = name;
      this.description = description;
      this.category = category;
      this.price = price;
      this.wallet = wallet;
      this.dateOfPurchase = dateOfPurchase;
      this.isFinished = isFinished;
      this.transactionIdReference = transactionIdReference;
   }

   BigDecimal getPrice() {
      return price;
   }

   Long getId() {
      return id;
   }


   String getName() {
      return name;
   }


   String getDescription() {
      return description;
   }


   LocalDateTime getDateOfPurchase() {
      return dateOfPurchase;
   }


   Boolean getFinished() {
      return isFinished;
   }


   Long getTransactionIdReference() {
      return transactionIdReference;
   }


   public SimpleCategoryQueryDto getCategory() {
      return category;
   }

}

