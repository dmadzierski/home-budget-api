package pl.wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.user.User;

import java.util.Optional;

@Repository
interface WalletRepository extends JpaRepository<Wallet, Long> {

   Optional<Wallet> getById(Long id);

   Optional<Wallet> getByIdAndUser(Long walletId, User user);

}
