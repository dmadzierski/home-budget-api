package pl.wallet;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SqlWalletRepository extends WalletRepository, JpaRepository<Wallet, Long> {
}
