package pl.wallet.transaction;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import pl.user.User;
import pl.user.UserFacade;
import pl.wallet.UserWallet;
import pl.wallet.Wallet;
import pl.wallet.WalletFacade;
import pl.wallet.category.CategoryDto;
import pl.wallet.category.CategoryFacade;
import pl.wallet.transaction.dto.TransactionDto;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
class TransactionController {
   private final TransactionService transactionService;
   private final UserFacade userFacade;
   private final WalletFacade walletFacade;
   private final CategoryFacade categoryFacade;


   TransactionDto addTransaction(Principal principal, Long walletId, Long categoryId, TransactionDto transactionDto) {
      if (transactionDto.getId() != null) throw new TransactionException(TransactionError.CAN_NOT_HAVE_ID);
      User user = userFacade.getUserByEmail(principal.getName());
      Wallet wallet = getWallet(user, walletId);
      CategoryDto categoryDto = getCategory(user, categoryId);
      Transaction transaction = TransactionMapper.toEntity(transactionDto);

      if (categoryDto.getTransactionType().ordinal() == 2 || categoryDto.getTransactionType().ordinal() == 3)
         transaction = transaction.toBuilder().isFinished(false).build();
      else
         transaction = transaction.toBuilder().isFinished(true).build();

      if (transaction.getDateOfPurchase() == null)
         transaction = transaction.toBuilder().dateOfPurchase(LocalDateTime.now()).build();

      transaction = transaction.toBuilder().wallet(wallet).build();
      transaction = categoryFacade.setCategory(user, transaction, categoryId);
      walletFacade.addTransaction(wallet, transaction);
      transaction = transactionService.save(transaction);
      return TransactionMapper.toDto(transaction);
   }

   private CategoryDto getCategory(User user, Long categoryId) {
      return categoryFacade.getCategory(user, categoryId);
   }

   private Wallet getWallet(User user, Long walletId) {
      return walletFacade.isUserWallet(user, walletId);
   }

   void removeTransaction(Principal principal, Long walletId, Long transactionId) {
      User user = userFacade.getUserByEmail(principal.getName());
      Wallet wallet = getWallet(user, walletId);
      Transaction transaction = transactionService.getTransaction(transactionId);
      wallet.removeTransaction(transaction);
      transactionService.removeTransaction(transactionId);
      walletFacade.saveWallet(wallet);
   }

   public List<TransactionDto> getWalletTransactions(Principal principal, Long walletId, Pageable pageable, Specification<Transaction> transactionSpecification) {
      User user = userFacade.getUser(principal);
      transactionSpecification.and(new UserWallet(user));
      return transactionService.getTransactionsByWalletId(pageable, transactionSpecification).stream().map(TransactionMapper::toDto).collect(Collectors.toList());
   }

   public TransactionDto getTransaction(Principal principal, Long walletId, Long transactionId) {
      User user = userFacade.getUser(principal);
      walletFacade.isUserWallet(user, walletId);
      return TransactionMapper.toDto(transactionService.getTransaction(transactionId));
   }

   public TransactionDto editTransaction(Principal principal, Long walletId, TransactionDto transactionDto) {
      User user = userFacade.getUser(principal);
      walletFacade.isUserWallet(user, walletId);

      Transaction transaction = transactionService.getTransaction(transactionDto.getId());
      transaction = transaction.toBuilder()
         .description(transactionDto.getDescription())
         .name(transactionDto.getName())
         .dateOfPurchase(transactionDto.getDateOfPurchase())
         .price(transactionDto.getPrice()).build();

      transactionService.save(transaction);
      return TransactionMapper.toDto(transaction);
   }

   public TransactionDto switchIsFinished(Principal principal, Long walletId, Long transactionId) {
      User user = userFacade.getUser(principal);
      walletFacade.isUserWallet(user, walletId);
      Transaction transaction = transactionService.getTransaction(transactionId);
      if (transaction.getFinished() == null)
         throw new TransactionException(TransactionError.CAN_NOT_SET_FINISHED);
      transaction = transaction.toBuilder().isFinished(transaction.getFinished()).build();
      Transaction updateTransaction = transactionService.save(transaction);
      return TransactionMapper.toDto(updateTransaction);

   }
}
