package pl.wallet.transaction;

import lombok.Builder;
import lombok.Getter;
import pl.wallet.SimpleWalletQueryDto;
import pl.wallet.category.SimpleCategoryQueryDto;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "transaction")
@Getter
public class SimpleTransactionQueryDto {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne
   private SimpleWalletQueryDto wallet;

   @OneToOne
   private SimpleCategoryQueryDto category;

   private BigDecimal price;

   @Builder(toBuilder = true)
   public SimpleTransactionQueryDto(Long id, SimpleWalletQueryDto wallet, SimpleCategoryQueryDto category, BigDecimal price) {
      this.id = id;
      this.wallet = wallet;
      this.category = category;
      this.price = price;
   }
}
