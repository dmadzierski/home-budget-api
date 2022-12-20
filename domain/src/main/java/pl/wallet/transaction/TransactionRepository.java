package pl.wallet.transaction;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

interface TransactionRepository {
   Optional<Transaction> findByIdAndWallet_Id(Long transactionId, Long id);

   @Query("SELECT t FROM Transaction t INNER JOIN Wallet w ON t.wallet = w WHERE w.id = :walletId")
   List<Transaction> getTransactionsByWalletId(@Param("walletId") Long walletId);
}
