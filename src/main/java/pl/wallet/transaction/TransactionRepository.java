package pl.wallet.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.wallet.Wallet;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  @Query("SELECT t FROM Transaction t INNER JOIN Wallet w ON t.wallet = w WHERE w.id = :walletId")
  List<Transaction> getTransactionsByWalletId (@Param("walletId") Long walletId);


  @Query("SELECT t FROM Transaction t INNER JOIN t.category c WHERE c.transactionType = 2 AND t.wallet.id = :walletId AND t.isFinished = false")
  List<Transaction> getBorrowTransaction (@Param("walletId") Long walletId);

  @Query("SELECT t FROM Transaction t INNER JOIN t.category c INNER JOIN t.wallet w WHERE c.transactionType = 3 AND w = :wallet AND t.isFinished = false")
  List<Transaction> getLoanTransaction (@Param("wallet") Wallet wallet);
}
