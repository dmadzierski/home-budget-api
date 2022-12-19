package pl.wallet.transaction;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class TransactionService {

   private final TransactionRepository transactionRepository;

   Transaction save(Transaction transaction) {
      return transactionRepository.save(transaction);
   }

   void removeTransaction(Long transactionId) {
      transactionRepository.deleteById(transactionId);
   }

   Transaction getTransactionByTransactionIdWallet(Long transactionId, Long id) {
      return transactionRepository.findByIdAndWallet_Id(transactionId, id).orElseThrow(() -> new TransactionException(TransactionError.NOT_FOUND));
   }


}
