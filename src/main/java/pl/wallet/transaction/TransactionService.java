package pl.wallet.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

  private TransactionRepository transactionRepository;

  @Autowired
  public TransactionService (TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  Transaction save (Transaction transaction) {
    return transactionRepository.save(transaction);
  }

  public List<Transaction> getTransactionByWalletId (Long walletId) {
    return transactionRepository.getTransactionsByWalletId(walletId);
  }
}
