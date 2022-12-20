package pl.wallet.transaction;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionFacade {
   private final TransactionRepository transactionRepository;

   public void removeWalletTransactions(Long walletId) {
      transactionRepository.getTransactionsByWalletId(walletId).forEach(transaction -> this.removeTransaction(transaction.getId()));
   }

   void removeTransaction(Long transactionId) {
      transactionRepository.deleteById(transactionId);
   }


}
