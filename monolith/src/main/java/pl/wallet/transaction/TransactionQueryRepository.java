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
   Set<TransactionDto> findAll(Specification<SimpleTransactionQueryDto> transactionSpecification, Pageable pageable);


   Transaction getTransactionById(Long transactionId);
}
