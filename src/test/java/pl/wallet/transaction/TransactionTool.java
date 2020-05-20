package pl.wallet.transaction;

import pl.test_tool.RandomTool;
import pl.wallet.WalletDto;
import pl.wallet.category.CategoryDto;

import java.security.Principal;

public class TransactionTool {

  public static TransactionDto getRandomTransaction () {
    return getTransactionBuilderWithNecessaryValue().build();
  }

  public static TransactionDto.TransactionDtoBuilder getTransactionBuilderWithNecessaryValue () {
    return TransactionDto.builder()
      .price(getRandomBalance())
      .name(getRandomName());
  }

  public static TransactionDto saveRandomTransaction (Principal principal, WalletDto walletDto, CategoryDto categoryDto, TransactionController transactionController) {
    return transactionController.addTransaction(principal, walletDto.getId(), categoryDto.getId(), getRandomTransaction());
  }

  public static TransactionDto saveRandomTransaction (Principal principal, WalletDto walletDto, CategoryDto categoryDto, TransactionResource transactionResource) {
    return transactionResource.addTransaction(principal, categoryDto.getId(), walletDto.getId(), getRandomTransaction()).getBody();
  }

  private static Integer getRandomBalance () {
    return RandomTool.getNumberInteger();
  }

  private static String getRandomName () {
    return RandomTool.getRandomString();
  }

}
