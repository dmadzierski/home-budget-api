package pl.wallet.category;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.wallet.transaction.TransactionType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Getter
@ToString
public class CategoryDto {

  private Long id;

  @NotNull
  private String name;

  private String description;

  @NotNull
  private TransactionType transactionType;

  @Builder
  public CategoryDto (@Null Long id, @NotNull String name, String description, @NotNull TransactionType transactionType) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.transactionType = transactionType;
  }
}
