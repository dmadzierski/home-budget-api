package pl.wallet;

import pl.user.User;

import java.util.Set;

public interface WalletQueryRepository extends org.springframework.data.repository.Repository<Wallet, Long> {

   Set<WalletDto> getByUser(User user);

}
