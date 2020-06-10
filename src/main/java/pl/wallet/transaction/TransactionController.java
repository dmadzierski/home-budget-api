package pl.wallet.transaction;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import pl.exception.ThereIsNoWalletsPropertyException;
import pl.exception.ThereIsNoYourPropertyException;
import pl.user.User;
import pl.user.UserService;
import pl.wallet.Wallet;
import pl.wallet.WalletService;
import pl.wallet.category.Category;
import pl.wallet.category.CategoryService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

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
    if(transaction.getTransactionIdReference() != null) transaction.setIsFinished(false);
    transaction.setWallet(wallet);
    transaction.setCategory(category);
    walletService.updateBalance(wallet, transaction);
    transaction = transactionService.save(transaction);
    return TransactionMapper.toDto(transaction);
  }

  private Category getCategory (User user, Long categoryId) {
    List<Category> collect = user.getCategories().stream().filter(c -> c.getId().equals(categoryId)).collect(Collectors.toList());
    try {
      return collect.get(0);
    } catch (NullPointerException e) {
      throw new ThereIsNoYourPropertyException();
    }
  }


  private Wallet getWallet (User user, Long walletId) {
    return walletService.isUserWallet(user, walletId);
  }

  void removeTransaction (Principal principal, Long walletId, Long transactionId) {
    User user = userService.getUserByEmail(principal.getName());
    Wallet wallet = getWallet(user, walletId);
    if(wallet.getTransactions().stream().anyMatch(k -> k.getId().equals(transactionId)))
      transactionService.removeTransactionById(transactionId);
    else
      throw new ThereIsNoWalletsPropertyException("The wallet does not contain this transaction");
  }


  public List<TransactionDto> getLoanTransaction (Principal principal, Long walletId) {
    User user = userService.getUserByPrincipal(principal);
    Wallet wallet = getWallet(user, walletId);
    List<Transaction> transaction = transactionService.getLoanTransaction(walletId);
    return transaction.stream().map(TransactionMapper::toDto).collect(Collectors.toList());
  }

  public List<TransactionDto> getBorrowTransaction (Principal principal, Long walletId) {
    User user = userService.getUserByPrincipal(principal);
    Wallet wallet = getWallet(user, walletId);
    List<Transaction> transaction = transactionService.getBorrowTransaction(walletId);
    return transaction.stream().map(TransactionMapper::toDto).collect(Collectors.toList());
  }
}
