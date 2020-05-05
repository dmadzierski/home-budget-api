package pl.wallet.transaction;

import pl.wallet.category.CategoryMapper;

public class TransactionMapper {
  private TransactionMapper () {
  }

  static Transaction toEntity (TransactionDto transactionDto) {
    Transaction transaction = new Transaction();
    transaction.setName(transactionDto.getName());
    transaction.setDescription(transactionDto.getDescription());
    transaction.setPrice(transactionDto.getPrice());
    return transaction;
  }

  public static TransactionDto toDto (Transaction transaction) {
    return TransactionDto.builder()
      .id(transaction.getId())
      .name(transaction.getName())
      .description(transaction.getDescription())
      .price(transaction.getPrice())
      .category(CategoryMapper.toDto(transaction.getCategory()))
      .build();
  }
}
