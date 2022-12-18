package pl.wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface WalletRepository extends JpaRepository<Wallet, Long> {

   Optional<Wallet> getById(Long id);


}
