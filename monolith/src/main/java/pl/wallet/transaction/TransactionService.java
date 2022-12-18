package pl.wallet.transaction;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class TransactionService {

   private final TransactionRepository transactionRepository;
   private final TransactionQueryRepository transactionQueryRepository;

   Transaction save(Transaction transaction) {
      return transactionRepository.save(transaction);
   }

   Transaction getTransaction(Long transactionId) {
      return transactionRepository.findById(transactionId).orElseThrow(() -> new TransactionException(TransactionError.NOT_FOUND));
   }

   List<Transaction> getTransactionsByWalletId(Pageable pageable, Specification<Transaction> transactionSpecification) {
      return transactionQueryRepository.findAll(transactionSpecification, pageable);
   }

   void removeTransaction(Long transactionId) {
      transactionRepository.deleteById(transactionId);
   }


}
