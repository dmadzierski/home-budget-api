package pl.wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.user.User;

import java.util.Optional;
import java.util.Set;

@Repository
interface WalletRepository extends JpaRepository<Wallet, Long> {


   Optional<Wallet> findByIdAndUser_Email(Long walletId, String email);

}
