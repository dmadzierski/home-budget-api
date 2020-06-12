package pl.wallet.transaction;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wallet.Wallet;

import java.util.List;

@Service
@AllArgsConstructor

public class TransactionService {

  private TransactionRepository transactionRepository;

  Transaction save (Transaction transaction) {
    return transactionRepository.save(transaction);
  }

  public List<Transaction> getTransactionsByWalletId (Long walletId) {
    return transactionRepository.getTransactionsByWalletId(walletId);
  }

  void removeTransactionById (Long transactionId) {
    transactionRepository.deleteById(transactionId);
  }

  public List<Transaction> getBorrowTransaction (Long walletId) {
    return transactionRepository.getBorrowTransaction(walletId);
  }

  public List<Transaction> getLoanTransaction (Wallet wallet) {
    return transactionRepository.getLoanTransaction(wallet);
  }

}
