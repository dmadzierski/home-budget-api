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
import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public class WalletDto {

  @Null(message = "New wallet can not have id")
  private Long id;

  @NotEmpty(message = "Wallet must have name")
  private String name;


  @NotNull(message = "Wallet must have balance")
  @NotEmpty(message = "Wallet must have balance")
  private Long balance;

  private List<TransactionDto> transactions;

  private List<UserDto> users;

  @Builder
  public WalletDto (@Null(message = "New wallet can not have id") Long id, @NotEmpty(message = "Wallet must have name") String name, @NotNull(message = "Wallet must have balance") Long balance, List<TransactionDto> transactions, List<UserDto> users) {
    this.id = id;
    this.name = name;
    this.balance = balance;
    this.transactions = transactions;
    this.users = users;
  }
}
