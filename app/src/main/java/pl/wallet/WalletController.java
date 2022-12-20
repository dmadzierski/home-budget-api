package pl.wallet;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import pl.user.UserFacade;
import pl.user.UserQueryService;
import pl.wallet.Wallet;
import pl.wallet.WalletRepository;
import pl.wallet.transaction.TransactionFacade;

import java.security.Principal;
import java.util.Set;

@Controller
@AllArgsConstructor
class WalletController {
   private final WalletRepository walletRepository;
   private final TransactionFacade transactionFacade;

   private final WalletQueryRepository walletQueryRepository;

   private final UserQueryService userQueryService;

   private final UserFacade userFacade;

   WalletDto addWallet(Principal principal, WalletDto walletDto) {
      if (walletDto.getId() != null) throw new WalletException(WalletError.CAN_NOT_HAVE_ID);
      userQueryService.findByEmail(principal.getName());
      Wallet wallet = WalletMapper.toEntity(walletDto);
      wallet = userFacade.addUserToWallet(wallet, principal.getName());
      wallet = walletRepository.save(wallet);
      return WalletMapper.toDto(wallet);
   }

   Set<WalletDto> getWallets(Principal principal) {
      userQueryService.findByEmail(principal.getName());
      return walletQueryRepository.getAllByUser_Email(principal.getName());
   }

   private void isUserWallet(Principal principal, Long walletId) {
      userQueryService.findByEmail(principal.getName());
      walletQueryRepository.findByIdAndUserEmail(walletId, principal.getName()).orElseThrow(() -> new WalletException(WalletError.NOT_FOUND));
   }

   void removeWallet(Principal principal, Long walletId) {
      isUserWallet(principal, walletId);
      transactionFacade.removeWalletTransactions(walletId);
      walletRepository.deleteById(walletId);
   }

   WalletDto getWallet(Principal principal, Long walletId) {
      userQueryService.findByEmail(principal.getName());
      return walletQueryRepository.findByIdAndUserEmail(walletId, principal.getName()).orElseThrow(() -> new WalletException(WalletError.NOT_FOUND));
   }

   WalletDto editWallet(Principal principal, WalletDto walletDto) {
      userQueryService.findByEmail(principal.getName());
      Wallet wallet = walletRepository.findByIdAndUser_Email(walletDto.getId(), principal.getName()).orElseThrow(() -> new WalletException(WalletError.NOT_FOUND));
      wallet = wallet.toBuilder().name(walletDto.getName()).build();
      Wallet updateWallet = walletRepository.save(wallet);
      return WalletMapper.toDto(updateWallet);
   }
}
