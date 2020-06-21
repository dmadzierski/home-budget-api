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

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor

public class TransactionController {
  private TransactionService transactionService;
  private UserService userService;
  private WalletService walletService;


  TransactionDto addTransaction (Principal principal, Long walletId, Long categoryId, TransactionDto transactionDto) {
    User user = userService.getUserByEmail(principal.getName());
    Wallet wallet = getWallet(user, walletId);
    Category category = getCategory(user, categoryId);
    Transaction transaction = TransactionMapper.toEntity(transactionDto);
    if(transaction.getId() != null) transaction.setId(null);

    if(category.getTransactionType().ordinal() == 2 || category.getTransactionType().ordinal() == 3)
      transaction.setIsFinished(false);
    else transaction.setIsFinished(true);
    if(transaction.getDateOfPurchase() == null)
      transaction.setDateOfPurchase(LocalDateTime.now());

    transaction.setWallet(wallet);
    transaction.setCategory(category);
    walletService.addTransaction(wallet, transaction);
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
    Optional<Transaction> transactionOptional = transactionService.getTransactionsByWalletId(walletId).stream().filter(k -> k.getId().equals(transactionId)).findAny();
    if(transactionOptional.isPresent()) {
      transactionService.removeTransactionById(transactionId);
      wallet.removeTransaction(transactionOptional.get());
      walletService.saveWallet(wallet);
    } else
      throw new ThereIsNoWalletsPropertyException("The wallet does not contain this transaction");
  }


  public List<TransactionDto> getLoanTransaction (Principal principal, Long walletId) {
    User user = userService.getUser(principal);
    Wallet wallet = getWallet(user, walletId);
    List<Transaction> transaction = transactionService.getLoanTransaction(wallet);
    return transaction.stream().map(TransactionMapper::toDto).collect(Collectors.toList());
  }

  public List<TransactionDto> getBorrowTransaction (Principal principal, Long walletId) {
    User user = userService.getUser(principal);
    getWallet(user, walletId);
    List<Transaction> transaction = transactionService.getBorrowTransaction(walletId);
    return transaction.stream().map(TransactionMapper::toDto).collect(Collectors.toList());
  }

  public List<TransactionDto> getWalletTransactions (Principal principal, Long walletId) {
    User user = userService.getUser(principal);
    walletService.isUserWallet(user, walletId);
    return transactionService.getTransactionsByWalletId(walletId).stream().map(TransactionMapper::toDto).collect(Collectors.toList());
  }

  public TransactionDto getTransaction (Principal principal, Long walletId, Long transactionId) {
    User user = userService.getUser(principal);
    getWalletTransactions(principal, walletId);
    return TransactionMapper.toDto(transactionService.getTransaction(transactionId));
  }

  public TransactionDto editTransaction (Principal principal, Long walletId, TransactionDto transactionDto) {
    User user = userService.getUser(principal);
    getWalletTransactions(principal, walletId);

    Transaction transaction = transactionService.getTransaction(transactionDto.getId());
    transaction.setDescription(transactionDto.getDescription());
    transaction.setName(transactionDto.getName());
    transaction.setDateOfPurchase(transactionDto.getDateOfPurchase());
    transaction.setPrice(transactionDto.getPrice());

    transactionService.update(transaction);
    return TransactionMapper.toDto(transaction);
  }
}
