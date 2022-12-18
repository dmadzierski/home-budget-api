package pl.wallet.transaction;

import pl.wallet.category.CategoryMapper;
import pl.wallet.transaction.dto.TransactionDto;

class TransactionMapper {

   static Transaction toEntity(TransactionDto transactionDto) {
      return Transaction.builder()
         .name(transactionDto.getName())
         .description(transactionDto.getDescription())
         .price(transactionDto.getPrice())
         .dateOfPurchase(transactionDto.getDateOfPurchase())
         .isFinished(transactionDto.getIsFinished())
         .transactionIdReference(transactionDto.getTransactionIdReference())
         .build();
   }

   public static TransactionDto toDto(Transaction transaction) {
      return TransactionDto.builder()
         .id(transaction.getId())
         .name(transaction.getName())
         .description(transaction.getDescription())
         .price(transaction.getPrice())
         .category(CategoryMapper.toDto(transaction.getCategory()))
         .dateOfPurchase(transaction.getDateOfPurchase())
         .isFinished(transaction.getFinished())
         .transactionIdReference(transaction.getTransactionIdReference())
         .build();
   }
}
