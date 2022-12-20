package pl.wallet.transaction;


import org.springframework.data.repository.Repository;

public interface SqlQueryTransactionRepository extends TransactionQueryRepository, Repository<TransactionDto, Long> {

}

