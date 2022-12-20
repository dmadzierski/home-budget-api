package pl.wallet;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.GreaterThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.LessThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, path = "/api/v1/wallet/{walletId}")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@CrossOrigin("${cors.allowed-origins}")
class TransactionResource {
   private final TransactionController transactionController;

   @BackTransactionOrSimpleTransaction(
      message = "Back transaction should have reference to loan or borrow transaction and transaction with set reference to another transaction should have set other transaction type")
   @PutMapping("/category/{categoryId}/transaction/add")
   ResponseEntity<TransactionDto> addTransaction(Principal principal, @PathVariable Long categoryId, @PathVariable Long walletId, @Valid @RequestBody TransactionDto transactionDto) {
      return ResponseEntity.status(HttpStatus.CREATED).body(transactionController.addTransaction(principal, walletId, categoryId, transactionDto));
   }

   @GetMapping(value = "/transaction/{transactionId}", consumes = MediaType.ALL_VALUE)
   ResponseEntity<TransactionDto> getTransaction(Principal principal, @PathVariable Long walletId, @PathVariable Long transactionId) {
      return ResponseEntity.ok(transactionController.getTransaction(principal, walletId, transactionId));
   }

   @PostMapping(value = "/transaction/edit", consumes = MediaType.ALL_VALUE)
   ResponseEntity<TransactionDto> editTransaction(Principal principal, @PathVariable Long walletId, @Valid @RequestBody TransactionDto transactionDto) {
      return ResponseEntity.status(HttpStatus.ACCEPTED).body(transactionController.editTransaction(principal, walletId, transactionDto));
   }

   @DeleteMapping(value = "/transaction/remove/{transactionId}", consumes = MediaType.ALL_VALUE, produces = MediaType.ALL_VALUE)
   ResponseEntity removeTransaction(Principal principal, @PathVariable Long walletId, @PathVariable Long transactionId) {
      transactionController.removeTransaction(principal, walletId, transactionId);
      return ResponseEntity.noContent().build();
   }

   @GetMapping(value = "/transactions", consumes = MediaType.ALL_VALUE)
   ResponseEntity<Set<TransactionDto>> getWalletTransactions(Principal principal,
                                                             @PageableDefault(page = 0, size = 40)
                                                             @SortDefault.SortDefaults({
                                                                @SortDefault(sort = "dateOfPurchase", direction = Sort.Direction.DESC),
                                                                @SortDefault(sort = "name", direction = Sort.Direction.ASC)})
                                                             Pageable pageable,
                                                             @And({
                                                                @Spec(path = "category.transactionType", params = "transactionType", spec = Equal.class),
                                                                @Spec(path = "isFinished", params = "isFinished", spec = Equal.class),
                                                                @Spec(path = "wallet.id", pathVars = "walletId", spec = Equal.class),
                                                                @Spec(path = "price", params = "minPrice", spec = GreaterThanOrEqual.class),
                                                                @Spec(path = "price", params = "maxPrice", spec = LessThanOrEqual.class),
                                                                @Spec(path = "dateOfPurchase", params = "start", spec = GreaterThanOrEqual.class),
                                                                @Spec(path = "dateOfPurchase", params = "end", spec = LessThanOrEqual.class)
                                                             })
                                                             Specification<SimpleTransactionQueryDto> transactionSpecification,
                                                             @PathVariable Long walletId) {
      return ResponseEntity.ok(transactionController.getWalletTransactions(principal, walletId, pageable, transactionSpecification));
   }

   @PostMapping(value = "/transaction/switchIsFinished/{transactionId}")
   ResponseEntity<TransactionDto> switchIsFinished(Principal principal, @PathVariable Long walletId, @PathVariable Long transactionId) {
      return ResponseEntity.ok(this.transactionController.switchIsFinished(principal, walletId, transactionId));
   }

   @GetMapping("/types")
   public ResponseEntity<List<TransactionType>> getTransactionsType() {
      return ResponseEntity.ok(Arrays.stream(TransactionType.values()).collect(Collectors.toList()));
   }
}
