package pl.wallet;

import org.springframework.data.repository.Repository;

interface SqlWalletQueryRepository extends WalletRepository, Repository<WalletDto, Long> {
}
