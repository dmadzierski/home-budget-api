package pl.wallet.transaction;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Validated
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, path = "/wallet/{walletId}")
@AllArgsConstructor
@CrossOrigin("${cors.allowed-origins}")
public class TransactionResource {
  private TransactionController transactionController;

  @BackTransactionOrSimpleTransaction(
    message = "Back transaction should have reference to loan or borrow transation and transaction with set reference to another transaction should have set other other transaction type",
    message2 = "Transaction with set reference to another transaction should have set other other transaction type")
  @PutMapping("/category/{categoryId}/transaction/add")
  public ResponseEntity<TransactionDto> addTransaction (Principal principal, @PathVariable Long categoryId, @PathVariable Long walletId, @Valid @RequestBody TransactionDto transactionDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(transactionController.addTransaction(principal, walletId, categoryId, transactionDto));
  }

  @DeleteMapping(value = "/transaction/remove/{transactionId}", consumes = MediaType.ALL_VALUE, produces = MediaType.ALL_VALUE)
  public ResponseEntity removeTransaction (Principal principal, @PathVariable Long walletId, @PathVariable Long transactionId) {
    transactionController.removeTransaction(principal, walletId, transactionId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping(value = "/loan_transaction", consumes = MediaType.ALL_VALUE)
  public ResponseEntity<List<TransactionDto>> getLoanTransaction (Principal principal, @PathVariable Long walletId) {
    return ResponseEntity.ok(transactionController.getLoanTransaction(principal, walletId));
  }

  @GetMapping(value = "/borrow_transaction", consumes = MediaType.ALL_VALUE)
  public ResponseEntity<List<TransactionDto>> getBorrowTransaction (Principal principal, @PathVariable Long walletId) {
    return ResponseEntity.ok(transactionController.getBorrowTransaction(principal, walletId));
  }

  @GetMapping(value = "/transactions", consumes = MediaType.ALL_VALUE)
  public ResponseEntity<List<TransactionDto>> getWalletTransactions (Principal principal, @PathVariable Long walletId) {
    return ResponseEntity.ok(transactionController.getWalletTransactions(principal, walletId));
  }

}
