package pl.wallet.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SqlTransactionRepository extends TransactionRepository, JpaRepository<Transaction, Long> {
}
