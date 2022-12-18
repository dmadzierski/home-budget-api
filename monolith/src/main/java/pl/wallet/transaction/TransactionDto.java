package pl.wallet.transaction;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import pl.wallet.category.CategoryDto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
public class TransactionDto {

   private final Long id;

   @Length(min = 1, message = "Transaction should have name")
   @NotNull(message = "Transaction must have name")
   private final String name;

   private final String description;

   private final CategoryDto category;

   @NotNull(message = "Transaction must have price")
   private final BigDecimal price;

   private final LocalDateTime dateOfPurchase;

   private final Long transactionIdReference;

   private final Boolean isFinished;

   @Builder(toBuilder = true)
   TransactionDto(Long id,
                  @NotNull(message = "Transaction should have name") @Length(min = 1, message = "Transaction should have name") String name,
                  String description,
                  CategoryDto category,
                  @NotNull(message = "Transaction must have price") BigDecimal price, LocalDateTime dateOfPurchase,
                  Long transactionIdReference,
                  Boolean isFinished) {
      this.isFinished = isFinished;
      this.id = id;
      this.name = name;
      this.description = description;
      this.category = category;
      this.price = price;
      this.dateOfPurchase = dateOfPurchase;
      this.transactionIdReference = transactionIdReference;
   }
}
