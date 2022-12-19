package pl.wallet.transaction;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TransactionQueryRepository extends org.springframework.data.repository.Repository<Transaction, Long> {
   Set<TransactionDto> findAll(Specification<Transaction> transactionSpecification, Pageable pageable);


   @Query("SELECT t FROM Transaction t INNER JOIN Wallet w ON t.wallet = w WHERE w.id = :walletId")
   List<TransactionDto> getTransactionsByWalletId(@Param("walletId") Long walletId);


   Transaction getTransactionById(Long transactionId);
}
