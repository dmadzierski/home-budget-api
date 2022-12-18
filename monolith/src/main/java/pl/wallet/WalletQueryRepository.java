package pl.wallet;

import pl.user.User;

import java.util.List;
import java.util.Optional;

public interface WalletQueryRepository extends org.springframework.data.repository.Repository<Wallet, Long> {

   List<Wallet> getByUser(User user);

   Optional<Wallet> getByIdAndUser(Long walletId, User user);
}
