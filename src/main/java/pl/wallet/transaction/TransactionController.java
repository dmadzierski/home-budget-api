package pl.wallet.transaction;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import pl.user.User;
import pl.user.UserService;
import pl.wallet.Wallet;
import pl.wallet.WalletService;
import pl.wallet.category.Category;
import pl.wallet.category.CategoryService;

import java.security.Principal;

@Controller
@AllArgsConstructor

public class TransactionController {
  private TransactionService transactionService;
  private UserService userService;
  private WalletService walletService;
  private CategoryService categoryService;


  TransactionDto addTransaction (Principal principal, Long walletId, Long categoryId, TransactionDto transactionDto) {

    User user = userService.getUserByEmail(principal.getName());
    Wallet wallet = getWallet(user, walletId);
    Category category = getCategory(user, categoryId);

    Transaction transaction = TransactionMapper.toEntity(transactionDto);

    transaction.setWallet(wallet);
    transaction.setCategory(category);

    walletService.updateBalance(wallet, transaction);

    transaction = transactionService.save(transaction);

    return TransactionMapper.toDto(transaction);
  }

  private Category getCategory (User user, Long categoryId) {
    return categoryService.isUserCategory(user, categoryId);
  }


  private Wallet getWallet (User user, Long walletId) {
    return walletService.isUserWallet(user, walletId);
  }
}
