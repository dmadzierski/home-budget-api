package pl.wallet.transaction;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("${cors.allowed-origins}")
public class TransactionTypeResource {
  @GetMapping("/types")
  public ResponseEntity<List<TransactionType>> getTransactionsType () {
    return ResponseEntity.ok(Arrays.stream(TransactionType.values()).collect(Collectors.toList()));
  }
}
