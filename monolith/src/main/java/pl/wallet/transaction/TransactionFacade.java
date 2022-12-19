package pl.wallet.transaction;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TransactionFacade {
   private final TransactionQueryRepository transactionQueryRepository;
   private final TransactionRepository transactionRepository;

   public void removeWalletTransactions(Long walletId) {
      transactionQueryRepository.getTransactionsByWalletId(walletId).forEach(transaction -> this.removeTransaction(transaction.getId()));
   }

   void removeTransaction(Long transactionId) {
      transactionRepository.deleteById(transactionId);
   }


}
