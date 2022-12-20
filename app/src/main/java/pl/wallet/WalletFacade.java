package pl.wallet;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wallet.transaction.SimpleTransactionQueryDto;

import java.math.BigDecimal;

@AllArgsConstructor
@Service
public class WalletFacade {

   private final WalletRepository walletRepository;


   public SimpleWalletQueryDto addWalletToTransaction(SimpleTransactionQueryDto transaction, Long walletId) {
      return WalletMapper.toQueryDto(walletRepository.findById(walletId).orElseThrow(() -> new WalletException(WalletError.NOT_FOUND)));
   }

   public Wallet saveWallet(Wallet wallet) {
      return walletRepository.save(wallet);
   }

   public SimpleWalletQueryDto getDefaultWallet() {
      return WalletMapper.toQueryDto(saveWallet(createDefaultWallet()));
   }

   private Wallet createDefaultWallet() {
      return Wallet.builder().name("Wallet").balance(BigDecimal.ZERO).build();
   }

   public void removeTransactionFromWalletAndSave(Long walletId, SimpleTransactionQueryDto transaction) {
      Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new WalletException(WalletError.NOT_FOUND));
      wallet.removeTransaction(transaction);
      walletRepository.save(wallet);
   }
}
