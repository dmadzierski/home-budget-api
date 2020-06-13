package pl.wallet;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import pl.user.UserDto;
import pl.wallet.transaction.TransactionDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.util.List;

@ToString
@Getter
@EqualsAndHashCode
public class WalletDto {

  @Null(message = "New wallet can not have id")
  private Long id;

  @NotEmpty(message = "Wallet must have name")
  private String name;

  @NotNull(message = "Wallet must have balance")
  private BigDecimal balance;

  private List<TransactionDto> transactions;

  private UserDto user;

  @Builder
  public WalletDto (@Null(message = "New wallet can not have id") Long id,
                    @NotEmpty(message = "Wallet must have name") String name,
                    @NotNull(message = "Wallet must have balance") BigDecimal balance,
                    List<TransactionDto> transactions,
                    UserDto user) {
    this.id = id;
    this.name = name;
    this.balance = balance;
    this.transactions = transactions;
    this.user = user;
  }
}
