package pl.wallet.transaction;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import pl.user.UserFacade;
import pl.user.UserQueryService;
import pl.wallet.SimpleWalletQueryDto;
import pl.wallet.WalletFacade;
import pl.wallet.WalletQueryService;
import pl.wallet.category.CategoryDto;
import pl.wallet.category.CategoryFacade;
import pl.wallet.category.CategoryQueryService;
import pl.wallet.category.SimpleCategoryQueryDto;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
class TransactionController {
   private final TransactionRepository transactionRepository;

   private final UserFacade userFacade;
   private final WalletFacade walletFacade;
   private final CategoryFacade categoryFacade;
   private final CategoryQueryService categoryQueryService;
   private final TransactionQueryRepository transactionQueryRepository;
   private final UserQueryService userQueryService;
   private final WalletQueryService walletQueryService;


   TransactionDto addTransaction(Principal principal, Long walletId, Long categoryId, TransactionDto transactionDto) {
      if (transactionDto.getId() != null) throw new TransactionException(TransactionError.CAN_NOT_HAVE_ID);
      userQueryService.findByEmail(principal.getName());
      walletQueryService.findByIdAndUser_Email(walletId, principal.getName());
      CategoryDto categoryDto = getCategory(principal.getName(), categoryId);
      Transaction transaction = TransactionMapper.toEntity(transactionDto);

      if (categoryDto.getTransactionType().ordinal() == 2 || categoryDto.getTransactionType().ordinal() == 3)
         transaction = transaction.toBuilder().isFinished(false).build();
      else
         transaction = transaction.toBuilder().isFinished(true).build();

      if (transaction.getDateOfPurchase() == null)
         transaction = transaction.toBuilder().dateOfPurchase(LocalDateTime.now()).build();

      SimpleWalletQueryDto simpleWalletQueryDto = walletFacade.addWalletToTransaction(TransactionMapper.toQueryDto(transaction), walletId);
      SimpleCategoryQueryDto simpleCategoryQueryDto = categoryFacade.setCategory(principal.getName(), TransactionMapper.toQueryDto(transaction), categoryId);
      transaction = transaction.toBuilder().wallet(simpleWalletQueryDto).category(simpleCategoryQueryDto).build();
      transaction = transactionRepository.save(transaction);
      return TransactionMapper.toDto(transaction);
   }

   private CategoryDto getCategory(String email, Long categoryId) {
      return categoryQueryService.findByIdAndUserEmail(categoryId, email);
   }

   void removeTransaction(Principal principal, Long walletId, Long transactionId) {
      userQueryService.findByEmail(principal.getName());
      walletQueryService.findByIdAndUser_Email(walletId, principal.getName());
      Transaction transaction = transactionRepository.findByIdAndWallet_Id(transactionId, walletId).orElseThrow(() -> new TransactionException(TransactionError.NOT_FOUND));
      walletFacade.removeTransactionFromWalletAndSave(walletId, TransactionMapper.toQueryDto(transaction));
      transactionRepository.deleteById(transactionId);
   }

   public Set<TransactionDto> getWalletTransactions(Principal principal, Long walletId, Pageable pageable, Specification<SimpleTransactionQueryDto> transactionSpecification) {
      userQueryService.findByEmail(principal.getName());
      userFacade.addFilterByUser(transactionSpecification, principal.getName());
      return transactionQueryRepository.findAll(transactionSpecification, pageable);
   }

   public TransactionDto getTransaction(Principal principal, Long walletId, Long transactionId) {
      userQueryService.findByEmail(principal.getName());
      walletQueryService.findByIdAndUser_Email(walletId, principal.getName());
      return TransactionMapper.toDto(transactionQueryRepository.getTransactionById(transactionId));
   }

   public TransactionDto editTransaction(Principal principal, Long walletId, TransactionDto transactionDto) {
      userQueryService.findByEmail(principal.getName());
      walletQueryService.findByIdAndUser_Email(walletId, principal.getName());

      Transaction transaction = transactionQueryRepository.getTransactionById(transactionDto.getId());
      transaction = transaction.toBuilder()
         .description(transactionDto.getDescription())
         .name(transactionDto.getName())
         .dateOfPurchase(transactionDto.getDateOfPurchase())
         .price(transactionDto.getPrice()).build();

      transactionRepository.save(transaction);
      return TransactionMapper.toDto(transaction);
   }

   public TransactionDto switchIsFinished(Principal principal, Long walletId, Long transactionId) {
      userQueryService.findByEmail(principal.getName());
      walletQueryService.findByIdAndUser_Email(walletId, principal.getName());
      Transaction transaction = transactionQueryRepository.getTransactionById(transactionId);
      if (transaction.getFinished() == null)
         throw new TransactionException(TransactionError.CAN_NOT_SET_FINISHED);
      transaction = transaction.toBuilder().isFinished(transaction.getFinished()).build();
      Transaction updateTransaction = transactionRepository.save(transaction);
      return TransactionMapper.toDto(updateTransaction);

   }
}
