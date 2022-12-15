package pl.wallet.transaction;

import pl.wallet.category.CategoryMapper;

public class TransactionMapper {
  private TransactionMapper () {
  }

  static Transaction toEntity (TransactionDto transactionDto) {
    return Transaction.builder()
      .name(transactionDto.getName())
      .description(transactionDto.getDescription())
      .price(transactionDto.getPrice())
      .dateOfPurchase(transactionDto.getDateOfPurchase())
      .isFinished(transactionDto.getIsFinished())
      .transactionIdReference(transactionDto.getTransactionIdReference())
      .build();
  }

  public static TransactionDto toDto (Transaction transaction) {
    return TransactionDto.builder()
      .id(transaction.getId())
      .name(transaction.getName())
      .description(transaction.getDescription())
      .price(transaction.getPrice())
      .category(CategoryMapper.toDto(transaction.getCategory()))
      .dateOfPurchase(transaction.getDateOfPurchase())
      .isFinished(transaction.getIsFinished())
      .transactionIdReference(transaction.getTransactionIdReference())
      .build();
  }
}
