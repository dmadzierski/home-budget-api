package pl.wallet.transaction;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
