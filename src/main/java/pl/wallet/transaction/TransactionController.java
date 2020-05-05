package pl.wallet.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.user.User;
import pl.user.UserService;
import pl.wallet.Wallet;
import pl.wallet.WalletService;
import pl.wallet.category.Category;
import pl.wallet.category.CategoryService;

import java.security.Principal;

@Controller
public class TransactionController {
  private TransactionService transactionService;
  private UserService userService;
  private WalletService walletService;
  private CategoryService categoryService;

  @Autowired
  public TransactionController (TransactionService transactionService, UserService userService, WalletService walletService, CategoryService categoryService) {
    this.transactionService = transactionService;
    this.userService = userService;
    this.walletService = walletService;
    this.categoryService = categoryService;
  }


  TransactionDto addTransaction (Principal principal, Long walletId, Long categoryId, TransactionDto transactionDto) {
    User user = userService.getUserByEmail(principal.getName());
    Wallet wallet = getWallet(user, walletId);
    Category category = getCategory(user, categoryId);
    Transaction transaction = TransactionMapper.toEntity(transactionDto);
    wallet = walletService.changeBalance(wallet, transaction);
    transaction.setWallet(wallet);
    transaction.setCategory(category);
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
