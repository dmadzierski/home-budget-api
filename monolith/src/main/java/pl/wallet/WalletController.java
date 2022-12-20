package pl.wallet;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import pl.user.*;
import pl.wallet.transaction.TransactionFacade;

import java.security.Principal;
import java.util.Set;

@Controller
@AllArgsConstructor
class WalletController {
   private final WalletRepository walletRepository;
   private final TransactionFacade transactionFacade;

   private final WalletQueryRepository walletQueryRepository;

   private final UserQueryRepository userQueryRepository;

   private  final UserFacade userFacade;

   WalletDto addWallet(Principal principal, WalletDto walletDto) {
      if (walletDto.getId() != null) throw new WalletException(WalletError.CAN_NOT_HAVE_ID);
      userQueryRepository.findByEmail(principal.getName()).orElseThrow(() -> new UserException(UserError.NOT_FOUND));
      Wallet wallet = WalletMapper.toEntity(walletDto);
      wallet = userFacade.addUserToWallet(wallet,principal.getName());
      wallet = walletRepository.save(wallet);
      return WalletMapper.toDto(wallet);
   }

   Set<WalletDto> getWallets(Principal principal) {
      userQueryRepository.findByEmail(principal.getName()).orElseThrow(() -> new UserException(UserError.NOT_FOUND));
      return walletQueryRepository.getAllByUser_Email(principal.getName());
   }

   private void isUserWallet(Principal principal, Long walletId) {
      UserDto user = userQueryRepository.findByEmail(principal.getName()).orElseThrow(() -> new UserException(UserError.NOT_FOUND));
      walletQueryRepository.findByIdAndUser_Email(walletId,principal.getName()).orElseThrow(() -> new WalletException(WalletError.NOT_FOUND));
   }

   void removeWallet(Principal principal, Long walletId) {
      isUserWallet(principal, walletId);
      transactionFacade.removeWalletTransactions(walletId);
      walletRepository.deleteById(walletId);
   }

   WalletDto getWallet(Principal principal, Long walletId) {
       userQueryRepository.findByEmail(principal.getName()).orElseThrow(() -> new UserException(UserError.NOT_FOUND));
      return walletQueryRepository.findByIdAndUser_Email(walletId, principal.getName()).orElseThrow(() -> new WalletException(WalletError.NOT_FOUND));
   }

   WalletDto editWallet(Principal principal, WalletDto walletDto) {
      UserDto user = userQueryRepository.findByEmail(principal.getName()).orElseThrow(() -> new UserException(UserError.NOT_FOUND));
      Wallet wallet = walletRepository.findByIdAndUser_Email(walletDto.getId(), principal.getName()).orElseThrow(() -> new WalletException(WalletError.NOT_FOUND));
      wallet = wallet.toBuilder().name(walletDto.getName()).build();
      Wallet updateWallet = walletRepository.save(wallet);
      return WalletMapper.toDto(updateWallet);
   }
}
