package pl.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Validated
@RestController
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@CrossOrigin(origins = "${cors.allowed-origins}")
class UserResource {

   private UserController userController;

   @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<UserDto> register(@RequestBody @Valid UserDto userDto) {
      return ResponseEntity.status(HttpStatus.CREATED).body(userController.addUserWithDefaultsResources(userDto));
   }

   @RequestMapping("/user")
   public Principal user(Principal principal) {
      return principal;
   }

   @GetMapping(value = "/profile", consumes = MediaType.ALL_VALUE)
   public UserDto getUser(Principal principal) {
      return userController.getUserByPrincipal(principal);
   }

   @PostMapping(value = "user/setFavoriteWallet")
   public UserDto setFavoriteWallet(Principal principal, @RequestBody(required = false) Long walletId) {
      return userController.setFavoriteWallet(principal, walletId);
   }
}
