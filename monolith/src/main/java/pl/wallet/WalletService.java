package pl.wallet;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.user.User;
import pl.wallet.transaction.Transaction;

import java.util.Set;

@Service
@AllArgsConstructor
public class WalletService {

   private final WalletRepository walletRepository;

   private Wallet getUserWallet(Long walletId) {
      return walletRepository.getById(walletId).orElseThrow(() -> new WalletException(WalletError.NOT_FOUND));
   }

   void removeWallet(Long walletId) {
      walletRepository.deleteById(walletId);
   }

   public Wallet isUserWallet(User user, Long walletId) {
      if (getWalletsByUser(user).stream().anyMatch(userWallet -> userWallet.getId().equals(walletId)))
         return getUserWallet(walletId);
      throw new WalletException(WalletError.NOT_FOUND);
   }

   Set<Wallet> getWalletsByUser(User user) {
      return walletRepository.getByUser(user);
   }

   public Wallet saveWallet(Wallet wallet) {
      return walletRepository.save(wallet);
   }

   public Wallet addTransaction(Wallet wallet, Transaction transaction) {
      wallet.addTransaction(transaction);
      return saveWallet(wallet);
   }


   public Wallet getUserWallet(String email, Long walletId) {
      return walletRepository.findByIdAndUser_Email(walletId, email).orElseThrow(() -> new WalletException(WalletError.NOT_FOUND));
   }
}
