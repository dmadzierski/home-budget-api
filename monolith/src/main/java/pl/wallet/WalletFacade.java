package pl.wallet;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.user.User;
import pl.wallet.transaction.Transaction;

import java.math.BigDecimal;

@AllArgsConstructor
@Service
public class WalletFacade {

   private final WalletRepository walletRepository;


   public Transaction addWalletToTransaction(Transaction transaction, Long walletId) {
      Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new WalletException(WalletError.NOT_FOUND));
      return transaction.toBuilder().wallet(wallet).build();
   }

   public WalletDto saveWallet(Wallet wallet) {
      return WalletMapper.toDto(walletRepository.save(wallet));
   }

   public WalletDto saveDefaultWallet(User user) {
      Wallet defaultWallet = createDefaultWallet();
      defaultWallet = defaultWallet.toBuilder().user(user).build();
      return saveWallet(defaultWallet);
   }

   private Wallet createDefaultWallet() {
      return Wallet.builder().name("Wallet").balance(BigDecimal.ZERO).build();
   }

   public void removeTransactionFromWalletAndSave(Long walletId, Transaction transaction) {
      Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new WalletException(WalletError.NOT_FOUND));
      wallet.removeTransaction(transaction);
      walletRepository.save(wallet);
   }
}
