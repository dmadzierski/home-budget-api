package pl.wallet;

import java.util.Optional;

interface WalletRepository {


   Optional<Wallet> findByIdAndUser_Email(Long walletId, String email);

}
