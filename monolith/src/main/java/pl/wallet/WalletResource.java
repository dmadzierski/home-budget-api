package pl.wallet;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Set;

@Validated
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/wallet")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@CrossOrigin("${cors.allowed-origins}")
class WalletResource {
   private final WalletController walletController;

   @PostMapping("/add")
   public ResponseEntity<WalletDto> addWallet(Principal principal, @Valid @RequestBody WalletDto walletDto) {
      return ResponseEntity.status(HttpStatus.CREATED).body(walletController.addWallet(principal, walletDto));
   }

   @GetMapping()
   public ResponseEntity<Set<WalletDto>> getWallets(Principal principal) {
      return ResponseEntity.ok(walletController.getWallets(principal));
   }

   @PostMapping("/edit")
   public ResponseEntity<WalletDto> editWallet(Principal principal, @Valid @RequestBody WalletDto walletDto) {
      return ResponseEntity.status(HttpStatus.CREATED).body(walletController.editWallet(principal, walletDto));
   }

   @DeleteMapping("/remove/{walletId}")
   public ResponseEntity removeWallet(Principal principal, @PathVariable Long walletId) {
      walletController.removeWallet(principal, walletId);
      return ResponseEntity.noContent().build();
   }

   @GetMapping("/{walletId}")
   public ResponseEntity<WalletDto> getWallet(Principal principal, @PathVariable Long walletId) {
      return ResponseEntity.ok(walletController.getWallet(principal, walletId));
   }

}
