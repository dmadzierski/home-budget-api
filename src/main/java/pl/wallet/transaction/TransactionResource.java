package pl.wallet.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Validated
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, path = "/wallet/{walletId}")
public class TransactionResource {
  private TransactionController transactionController;

  @Autowired
  public TransactionResource (TransactionController transactionController) {
    this.transactionController = transactionController;
  }

  @PutMapping("/category/{categoryId}/transaction/add")
  public ResponseEntity<TransactionDto> addTransaction (Principal principal, @PathVariable Long categoryId, @PathVariable Long walletId, @Valid @RequestBody TransactionDto transactionDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(transactionController.addTransaction(principal, walletId, categoryId, transactionDto));
  }


}
