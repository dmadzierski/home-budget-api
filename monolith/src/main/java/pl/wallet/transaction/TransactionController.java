package pl.wallet.transaction;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import pl.user.User;
import pl.user.UserProvider;
import pl.wallet.UserWallet;
import pl.wallet.Wallet;
import pl.wallet.WalletProvider;
import pl.wallet.category.Category;
import pl.wallet.category.CategoryProvider;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
class TransactionController {
   private final TransactionService transactionService;
   private final UserProvider userProvider;
   private final WalletProvider walletProvider;
   private final CategoryProvider categoryProvider;


   TransactionDto addTransaction(Principal principal, Long walletId, Long categoryId, TransactionDto transactionDto) {
      if (transactionDto.getId() != null) throw new TransactionException(TransactionError.CAN_NOT_HAVE_ID);
      User user = userProvider.getUserByEmail(principal.getName());
      Wallet wallet = getWallet(user, walletId);
      Category category = getCategory(user, categoryId);
      Transaction transaction = TransactionMapper.toEntity(transactionDto);

      if (category.getTransactionType().ordinal() == 2 || category.getTransactionType().ordinal() == 3)
         transaction.setIsFinished(false);
      else transaction.setIsFinished(true);

      if (transaction.getDateOfPurchase() == null)
         transaction.setDateOfPurchase(LocalDateTime.now());

      transaction.setWallet(wallet);
      transaction.setCategory(category);
      walletProvider.addTransaction(wallet, transaction);
      transaction = transactionService.save(transaction);
      return TransactionMapper.toDto(transaction);
   }

   private Category getCategory(User user, Long categoryId) {
      return categoryProvider.getCategory(user, categoryId);
   }

   private Wallet getWallet(User user, Long walletId) {
      return walletProvider.isUserWallet(user, walletId);
   }

   void removeTransaction(Principal principal, Long walletId, Long transactionId) {
      User user = userProvider.getUserByEmail(principal.getName());
      Wallet wallet = getWallet(user, walletId);
      Transaction transaction = transactionService.getTransaction(transactionId);
      wallet.removeTransaction(transaction);
      transactionService.removeTransaction(transactionId);
      walletProvider.saveWallet(wallet);
   }

   public List<TransactionDto> getWalletTransactions(Principal principal, Long walletId, Pageable pageable, Specification<Transaction> transactionSpecification) {
      User user = userProvider.getUser(principal);
      transactionSpecification.and(new UserWallet(user));
      return transactionService.getTransactionsByWalletId(pageable, transactionSpecification).stream().map(TransactionMapper::toDto).collect(Collectors.toList());
   }

   public TransactionDto getTransaction(Principal principal, Long walletId, Long transactionId) {
      User user = userProvider.getUser(principal);
      walletProvider.isUserWallet(user, walletId);
      return TransactionMapper.toDto(transactionService.getTransaction(transactionId));
   }

   public TransactionDto editTransaction(Principal principal, Long walletId, TransactionDto transactionDto) {
      User user = userProvider.getUser(principal);
      walletProvider.isUserWallet(user, walletId);

      Transaction transaction = transactionService.getTransaction(transactionDto.getId());
      transaction.setDescription(transactionDto.getDescription());
      transaction.setName(transactionDto.getName());
      transaction.setDateOfPurchase(transactionDto.getDateOfPurchase());
      transaction.setPrice(transactionDto.getPrice());

      transactionService.save(transaction);
      return TransactionMapper.toDto(transaction);
   }

   public TransactionDto switchIsFinished(Principal principal, Long walletId, Long transactionId) {
      User user = userProvider.getUser(principal);
      walletProvider.isUserWallet(user, walletId);
      Transaction transaction = transactionService.getTransaction(transactionId);
      if (transaction.getIsFinished() == null)
         throw new TransactionException(TransactionError.CAN_NOT_SET_FINISHED);
      transaction.setIsFinished(!transaction.getIsFinished());
      Transaction updateTransaction = transactionService.save(transaction);
      return TransactionMapper.toDto(updateTransaction);

   }
}
