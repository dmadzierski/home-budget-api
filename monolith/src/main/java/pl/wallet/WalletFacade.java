package pl.wallet;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.user.User;
import pl.wallet.transaction.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class WalletFacade {

   private final WalletRepository walletRepository;
   private final WalletQueryRepository walletQueryRepository;

   public Wallet saveWallet(Wallet wallet) {
      return walletRepository.save(wallet);
   }

   List<Wallet> getWalletsByUser(User user) {
      return walletQueryRepository.getByUser(user);
   }

   void removeWallet(Long walletId) {
      walletRepository.deleteById(walletId);
   }

   private Wallet getUserWallet(Long walletId) {
      return walletRepository.getById(walletId).orElseThrow(() -> new WalletException(WalletError.NOT_FOUND));
   }

   public Wallet isUserWallet(User user, Long walletId) {
      if (this.getWalletsByUser(user).stream().anyMatch(userWallet -> userWallet.getId().equals(walletId)))
         return getUserWallet(walletId);
      throw new WalletException(WalletError.NOT_FOUND);
   }


   public Wallet addTransaction(Wallet wallet, Transaction transaction) {
      wallet.addTransaction(transaction);
      return saveWallet(wallet);
   }

   public Wallet saveDefaultWallet(User user) {
      Wallet defaultWallet = createDefaultWallet();
      defaultWallet = defaultWallet.toBuilder().user(user).build();
      return saveWallet(defaultWallet);
   }

   public Optional<Wallet> getUserWallet(User user, Long walletId) {
      return walletQueryRepository.getByIdAndUser(walletId, user);
   }

   private Wallet createDefaultWallet() {
      return Wallet.builder().name("Wallet").balance(BigDecimal.ZERO).build();
   }
}
