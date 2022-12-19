package pl.wallet.transaction;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import pl.user.*;
import pl.wallet.*;
import pl.wallet.category.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Set;

@Controller
@AllArgsConstructor
class TransactionController {
   private final TransactionService transactionService;

   private final UserFacade userFacade;
   private final WalletFacade walletFacade;
   private final CategoryFacade categoryFacade;
   private final CategoryQueryRepository categoryQueryRepository;
   private final TransactionQueryRepository transactionQueryRepository;
   private final UserQueryRepository userQueryRepository;
   private final WalletQueryRepository walletQueryRepository;


   TransactionDto addTransaction(Principal principal, Long walletId, Long categoryId, TransactionDto transactionDto) {
      if (transactionDto.getId() != null) throw new TransactionException(TransactionError.CAN_NOT_HAVE_ID);
      UserDto userDto = userQueryRepository.findByEmail(principal.getName()).orElseThrow(() -> new UserException(UserError.NOT_FOUND));
      WalletDto walletDto = walletQueryRepository.findByIdAndUser_Email(walletId, principal.getName()).orElseThrow(() -> new WalletException(WalletError.NOT_FOUND));
      CategoryDto categoryDto = getCategory(principal.getName(), categoryId);
      Transaction transaction = TransactionMapper.toEntity(transactionDto);

      if (categoryDto.getTransactionType().ordinal() == 2 || categoryDto.getTransactionType().ordinal() == 3)
         transaction = transaction.toBuilder().isFinished(false).build();
      else
         transaction = transaction.toBuilder().isFinished(true).build();

      if (transaction.getDateOfPurchase() == null)
         transaction = transaction.toBuilder().dateOfPurchase(LocalDateTime.now()).build();

      transaction = walletFacade.addWalletToTransaction(transaction, walletId);
      transaction = categoryFacade.setCategory(principal.getName(), transaction, categoryId);
      transaction = walletFacade.addWalletToTransaction(transaction, walletId);
      transaction = transactionService.save(transaction);
      return TransactionMapper.toDto(transaction);
   }

   private CategoryDto getCategory(String email, Long categoryId) {
      return categoryQueryRepository.findByIdAndUserEmail(categoryId, email).orElseThrow(() -> new CategoryException(CategoryError.NOT_FOUND));
   }

   void removeTransaction(Principal principal, Long walletId, Long transactionId) {
      UserDto userDto = userQueryRepository.findByEmail(principal.getName()).orElseThrow(() -> new UserException(UserError.NOT_FOUND));
      WalletDto walletDto = walletQueryRepository.findByIdAndUser_Email(walletId, principal.getName()).orElseThrow(() -> new WalletException(WalletError.NOT_FOUND));
      Transaction transaction = transactionService.getTransactionByTransactionIdWallet(transactionId, walletId);
      walletFacade.removeTransactionFromWalletAndSave(walletId, transaction);
      transactionService.removeTransaction(transactionId);
   }

   public Set<TransactionDto> getWalletTransactions(Principal principal, Long walletId, Pageable pageable, Specification<Transaction> transactionSpecification) {
      UserDto userDto = userQueryRepository.findByEmail(principal.getName()).orElseThrow(() -> new UserException(UserError.NOT_FOUND));
      userFacade.addFilterByUser(transactionSpecification, principal.getName());
      return transactionQueryRepository.findAll(transactionSpecification, pageable);
   }

   public TransactionDto getTransaction(Principal principal, Long walletId, Long transactionId) {
      UserDto userDto = userQueryRepository.findByEmail(principal.getName()).orElseThrow(() -> new UserException(UserError.NOT_FOUND));
      walletQueryRepository.findByIdAndUser_Email(walletId, principal.getName()).orElseThrow(() -> new WalletException(WalletError.NOT_FOUND));
      return TransactionMapper.toDto(transactionQueryRepository.getTransactionById(transactionId));
   }

   public TransactionDto editTransaction(Principal principal, Long walletId, TransactionDto transactionDto) {
      UserDto userDto = userQueryRepository.findByEmail(principal.getName()).orElseThrow(() -> new UserException(UserError.NOT_FOUND));
      walletQueryRepository.findByIdAndUser_Email(walletId, principal.getName()).orElseThrow(() -> new WalletException(WalletError.NOT_FOUND));

      Transaction transaction = transactionQueryRepository.getTransactionById(transactionDto.getId());
      transaction = transaction.toBuilder()
         .description(transactionDto.getDescription())
         .name(transactionDto.getName())
         .dateOfPurchase(transactionDto.getDateOfPurchase())
         .price(transactionDto.getPrice()).build();

      transactionService.save(transaction);
      return TransactionMapper.toDto(transaction);
   }

   public TransactionDto switchIsFinished(Principal principal, Long walletId, Long transactionId) {
      UserDto userDto = userQueryRepository.findByEmail(principal.getName()).orElseThrow(() -> new UserException(UserError.NOT_FOUND));
      walletQueryRepository.findByIdAndUser_Email(walletId, principal.getName()).orElseThrow(() -> new WalletException(WalletError.NOT_FOUND));
      Transaction transaction = transactionQueryRepository.getTransactionById(transactionId);
      if (transaction.getFinished() == null)
         throw new TransactionException(TransactionError.CAN_NOT_SET_FINISHED);
      transaction = transaction.toBuilder().isFinished(transaction.getFinished()).build();
      Transaction updateTransaction = transactionService.save(transaction);
      return TransactionMapper.toDto(updateTransaction);

   }
}
