package pl.wallet;

import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

interface WalletQueryRepository extends org.springframework.data.repository.Repository<Wallet, Long> {

   @Query("SELECT w FROM Wallet w JOIN w.user u WHERE u.email = :email")
   Set<WalletDto> getAllByUser_Email(String email);


   Optional<WalletDto> findByIdAndUserEmail(Long id, String email);
}
