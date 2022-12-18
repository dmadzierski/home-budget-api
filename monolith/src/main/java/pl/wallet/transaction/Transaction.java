package pl.wallet.transaction;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.wallet.Wallet;
import pl.wallet.category.Category;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "transaction")
@ToString
public class Transaction {

   @Column(name = "transaction_id")
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false)
   private String name;

   private String description;

   @OneToOne
   private Category category;

   @Column(nullable = false)
   private BigDecimal price;

   @ManyToOne
   private Wallet wallet;

   private LocalDateTime dateOfPurchase;

   private Boolean isFinished;

   private Long transactionIdReference;

   @Builder
   public Transaction(String name, String description, Category category, BigDecimal price, Wallet wallet, LocalDateTime dateOfPurchase, Boolean isFinished, Long transactionIdReference) {
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

   void setPrice(BigDecimal price) {
      this.price = price;
   }

   Long getId() {
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

   String getDescription() {
      return description;
   }

   void setDescription(String description) {
      this.description = description;
   }

   Wallet getWallet() {
      return wallet;
   }

   void setWallet(Wallet wallet) {
      this.wallet = wallet;
   }

   LocalDateTime getDateOfPurchase() {
      return dateOfPurchase;
   }

   void setDateOfPurchase(LocalDateTime dateOfPurchase) {
      this.dateOfPurchase = dateOfPurchase;
   }

   Boolean getFinished() {
      return isFinished;
   }

   void setFinished(Boolean finished) {
      isFinished = finished;
   }

   Long getTransactionIdReference() {
      return transactionIdReference;
   }

   void setTransactionIdReference(Long transactionIdReference) {
      this.transactionIdReference = transactionIdReference;
   }

   public Category getCategory() {
      return category;
   }
   public void setCategory(Category category) {
      this.category = category;
   }
}

