package pl.wallet;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import pl.user.User;
import pl.user.UserService;
import pl.wallet.transaction.Transaction;
import pl.wallet.transaction.TransactionService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class WalletController {
  private WalletService walletService;
  private UserService userService;
  private TransactionService transactionService;

  WalletDto getWalletWithTransactions (Principal principal, Long walletId) {
    isUserWallet(principal, walletId);
    Wallet wallet = walletService.getWalletById(walletId);
    List<Transaction> transactions = transactionService.getTransactionByWalletId(walletId);
    return WalletMapper.toDtoWithTransaction(wallet, transactions);
  }

  WalletDto addWallet (Principal principal, WalletDto walletDto) {
    User user = userService.getUserByEmail(principal.getName());
    Wallet wallet = WalletMapper.toEntity(walletDto);
    wallet.addUser(user);
    wallet = walletService.saveWallet(wallet);
    walletDto = WalletMapper.toDto(wallet);
    return walletDto;
  }

  List<WalletDto> getWallets (Principal principal) {
    User user = userService.getUserByEmail(principal.getName());
    return walletService.getWalletsByUser(user).stream().map(WalletMapper::toDto).collect(Collectors.toList());
  }

  void removeWallet (Principal principal, Long walletId) {
    isUserWallet(principal, walletId);
    walletService.removeWallet(walletId);
  }

  private void isUserWallet (Principal principal, Long walletId) {
    User user = userService.getUserByEmail(principal.getName());
    walletService.isUserWallet(user, walletId);
  }
}
