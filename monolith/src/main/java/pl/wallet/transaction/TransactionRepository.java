package pl.wallet.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wallet.Wallet;

import java.util.Optional;

@Repository
interface TransactionRepository extends JpaRepository<Transaction, Long> {
   Optional<Transaction> findByIdAndWallet_Id(Long transactionId, Long id);
}
