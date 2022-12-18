package pl.wallet;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import pl.user.User;
import pl.user.UserProvider;
import pl.wallet.transaction.TransactionProvider;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
class WalletController {
   private final WalletProvider walletProvider;
   private final UserProvider userProvider;
   private final TransactionProvider transactionProvider;

   WalletDto addWallet(Principal principal, WalletDto walletDto) {
      if (walletDto.getId() != null) throw new WalletException(WalletError.CAN_NOT_HAVE_ID);
      User user = userProvider.getUser(principal);
      Wallet wallet = WalletMapper.toEntity(walletDto);
      wallet.setUser(user);
      wallet = walletProvider.saveWallet(wallet);
      return WalletMapper.toDto(wallet);
   }

   List<WalletDto> getWallets(Principal principal) {
      User user = userProvider.getUser(principal);
      return walletProvider.getWalletsByUser(user).stream().map(WalletMapper::toDto).collect(Collectors.toList());
   }

   private void isUserWallet(Principal principal, Long walletId) {
      User user = userProvider.getUser(principal);
      walletProvider.isUserWallet(user, walletId);
   }

   void removeWallet(Principal principal, Long walletId) {
      isUserWallet(principal, walletId);
      transactionProvider.removeWalletTransactions(walletId);
      walletProvider.removeWallet(walletId);
   }

   WalletDto getWallet(Principal principal, Long walletId) {
      User user = this.userProvider.getUser(principal);
      Wallet wallet = walletProvider.getUserWallet(user, walletId).orElseThrow(() -> new WalletException(WalletError.NOT_FOUND));
      return WalletMapper.toDto(wallet);
   }

   WalletDto editWallet(Principal principal, WalletDto walletDto) {
      User user = this.userProvider.getUser(principal);
      Wallet wallet = walletProvider.getUserWallet(user, walletDto.getId()).orElseThrow(() -> new WalletException(WalletError.NOT_FOUND));
      wallet.setName(walletDto.getName());
      Wallet updateWallet = walletProvider.saveWallet(wallet);
      return WalletMapper.toDto(updateWallet);
   }
}
