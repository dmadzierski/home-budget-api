package pl.wallet.transaction;


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
         .dateOfPurchase(transaction.getDateOfPurchase())
         .isFinished(transaction.getFinished())
         .transactionIdReference(transaction.getTransactionIdReference())
         .build();
   }

   public static SimpleTransactionQueryDto toQueryDto(Transaction transaction) {
      return SimpleTransactionQueryDto.builder()
         .id(transaction.getId())
         .price(transaction.getPrice())
         .build();
   }
}
