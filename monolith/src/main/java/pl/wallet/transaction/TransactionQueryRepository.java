package pl.wallet.transaction;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
interface TransactionQueryRepository extends org.springframework.data.repository.Repository<Transaction, Long> {
   Set<TransactionDto> findAll(Specification<SimpleTransactionQueryDto> transactionSpecification, Pageable pageable);


   Transaction getTransactionById(Long transactionId);
}
