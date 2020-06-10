package pl.wallet.transaction;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class TransactionService {

  private TransactionRepository transactionRepository;

  Transaction save (Transaction transaction) {
    return transactionRepository.save(transaction);
  }

  public List<Transaction> getTransactionByWalletId (Long walletId) {
    return transactionRepository.getTransactionsByWalletId(walletId);
  }

  void removeTransactionById (Long transactionId) {
    transactionRepository.deleteById(transactionId);
  }

  public List<Transaction> getBorrowTransaction (Long walletId) {
    return transactionRepository.getBorrowTransaction(walletId).stream().filter(transaction -> transaction.getWallet().getId().equals(walletId)).collect(Collectors.toList());
  }

  public List<Transaction> getLoanTransaction (Long walletId) {
    return transactionRepository.getLoanTransaction(walletId).stream().filter(transaction -> transaction.getWallet().getId().equals(walletId)).collect(Collectors.toList());
  }
}
