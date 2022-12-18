package pl.wallet;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import pl.user.User;
import pl.user.UserFacade;
import pl.wallet.transaction.TransactionFacade;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
class WalletController {
   private final WalletFacade walletFacade;
   private final UserFacade userFacade;
   private final TransactionFacade transactionFacade;

   WalletDto addWallet(Principal principal, WalletDto walletDto) {
      if (walletDto.getId() != null) throw new WalletException(WalletError.CAN_NOT_HAVE_ID);
      User user = userFacade.getUser(principal);
      Wallet wallet = WalletMapper.toEntity(walletDto);
      wallet.setUser(user);
      wallet = walletFacade.saveWallet(wallet);
      return WalletMapper.toDto(wallet);
   }

   List<WalletDto> getWallets(Principal principal) {
      User user = userFacade.getUser(principal);
      return walletFacade.getWalletsByUser(user).stream().map(WalletMapper::toDto).collect(Collectors.toList());
   }

   private void isUserWallet(Principal principal, Long walletId) {
      User user = userFacade.getUser(principal);
      walletFacade.isUserWallet(user, walletId);
   }

   void removeWallet(Principal principal, Long walletId) {
      isUserWallet(principal, walletId);
      transactionFacade.removeWalletTransactions(walletId);
      walletFacade.removeWallet(walletId);
   }

   WalletDto getWallet(Principal principal, Long walletId) {
      User user = this.userFacade.getUser(principal);
      Wallet wallet = walletFacade.getUserWallet(user, walletId).orElseThrow(() -> new WalletException(WalletError.NOT_FOUND));
      return WalletMapper.toDto(wallet);
   }

   WalletDto editWallet(Principal principal, WalletDto walletDto) {
      User user = this.userFacade.getUser(principal);
      Wallet wallet = walletFacade.getUserWallet(user, walletDto.getId()).orElseThrow(() -> new WalletException(WalletError.NOT_FOUND));
      wallet.setName(walletDto.getName());
      Wallet updateWallet = walletFacade.saveWallet(wallet);
      return WalletMapper.toDto(updateWallet);
   }
}
