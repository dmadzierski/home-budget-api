package pl.wallet.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface TransactionRepository extends JpaRepository<Transaction, Long> {
   Optional<Transaction> findByIdAndWallet_Id(Long transactionId, Long id);

   @Query("SELECT t FROM Transaction t INNER JOIN Wallet w ON t.wallet = w WHERE w.id = :walletId")
   List<TransactionDto> getTransactionsByWalletId(@Param("walletId") Long walletId);
}
