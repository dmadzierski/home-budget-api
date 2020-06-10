package pl.wallet.transaction;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.wallet.category.CategoryDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@ToString
public class TransactionDto {

  @Null(message = "New transaction can not have id")
  private Long id;

  @NotNull(message = "Transaction must have name")
  private String name;

  private String description;

  private CategoryDto category;

  @NotNull(message = "Transaction must have price")
  private BigDecimal price;

  private LocalDateTime addingTime;

  private Long transactionIdReference;

  private Boolean isFinished;

  @Builder
  public TransactionDto (@Null(message = "New transaction can not have id") Long id,
                         @NotNull(message = "Transaction should have name") String name,
                         String description,
                         CategoryDto category,
                         @NotNull(message = "Transaction must have price") BigDecimal price, LocalDateTime addingTime,
                         Long transactionIdReference,
                         Boolean isFinished) {
    this.isFinished = isFinished;
    this.id = id;
    this.name = name;
    this.description = description;
    this.category = category;
    this.price = price;
    this.addingTime = addingTime;
    this.transactionIdReference = transactionIdReference;
  }
}
