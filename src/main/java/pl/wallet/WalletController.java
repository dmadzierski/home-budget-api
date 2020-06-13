package pl.wallet;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import pl.exception.ThereIsNoYourPropertyException;
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
    Wallet wallet = walletService.getUserWalletById(walletId);
    List<Transaction> transactions = transactionService.getTransactionsByWalletId(walletId);
    return WalletMapper.toDtoWithTransaction(wallet, transactions);
  }

  WalletDto addWallet (Principal principal, WalletDto walletDto) {
    User user = userService.getUser(principal);
    Wallet wallet = WalletMapper.toEntity(walletDto);
    wallet.setUser(user);
    wallet = walletService.saveWallet(wallet);
    return walletDto = WalletMapper.toDto(wallet);

  }

  List<WalletDto> getWallets (Principal principal) {
    User user = userService.getUser(principal);
    return walletService.getWalletsByUser(user).stream().map(WalletMapper::toDto).collect(Collectors.toList());
  }

  void removeWallet (Principal principal, Long walletId) {
    isUserWallet(principal, walletId);
    walletService.removeWallet(walletId);
  }

  private void isUserWallet (Principal principal, Long walletId) {
    User user = userService.getUser(principal);
    walletService.isUserWallet(user, walletId);
  }

  public WalletDto getWallet (Principal principal, Long walletId) {
    User user = this.userService.getUser(principal);
    return WalletMapper.toDto(walletService.getUserWalletById(user, walletId).orElseThrow(ThereIsNoYourPropertyException::new));
  }
}
