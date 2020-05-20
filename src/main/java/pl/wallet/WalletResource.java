package pl.wallet;


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
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/wallet")
@CrossOrigin("http://localhost:4200")
@AllArgsConstructor
public class WalletResource {
  private WalletController walletController;


  // TODO the uri response should include error description when balance is not a number
  @PostMapping("/add")
  public ResponseEntity<WalletDto> addWallet (Principal principal, @Valid @RequestBody WalletDto walletDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(walletController.addWallet(principal, walletDto));
  }

  @GetMapping("")
  public ResponseEntity<List<WalletDto>> getWallets (Principal principal) {
    return ResponseEntity.ok(walletController.getWallets(principal));
  }

  @DeleteMapping("/remove/{walletId}")
  public ResponseEntity removeWallet (Principal principal, @PathVariable Long walletId) {
    walletController.removeWallet(principal, walletId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{walletId}")
  public ResponseEntity<WalletDto> getWallet (Principal principal, @PathVariable Long walletId) {
    return ResponseEntity.ok(walletController.getWalletWithTransactions(principal, walletId));
  }


}
