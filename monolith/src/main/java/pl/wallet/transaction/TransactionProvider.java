package pl.wallet.transaction;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TransactionProvider {
   private TransactionRepository transactionRepository;

   public void removeWalletTransactions(Long walletId) {
      transactionRepository.getTransactionsByWalletId(walletId).forEach(transaction -> this.removeTransaction(transaction.getId()));
   }

   void removeTransaction(Long transactionId) {
      transactionRepository.deleteById(transactionId);
   }
}
