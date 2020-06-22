package pl.wallet.transaction;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import pl.wallet.category.CategoryDto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@ToString
public class TransactionDto {

  private Long id;

  @Length(min = 1, message = "Transaction should have name")
  @NotNull(message = "Transaction must have name")
  private String name;

  private String description;

  private CategoryDto category;

  @NotNull(message = "Transaction must have price")
  private BigDecimal price;

  private LocalDateTime dateOfPurchase;

  private Long transactionIdReference;

  private Boolean isFinished;

  @Builder
  public TransactionDto (Long id,
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
