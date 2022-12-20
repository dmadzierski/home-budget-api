package pl.wallet.transaction;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
interface TransactionQueryRepository {
   Set<TransactionDto> findAll(Specification<SimpleTransactionQueryDto> transactionSpecification, Pageable pageable);


   Transaction getTransactionById(Long transactionId);
}
