package pl.wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.user.User;

import java.util.List;
import java.util.Optional;

public interface WalletQueryRepository extends JpaRepository<Wallet, Long> {

   List<Wallet> getByUser(User user);

   Optional<Wallet> getByIdAndUser(Long walletId, User user);
}
