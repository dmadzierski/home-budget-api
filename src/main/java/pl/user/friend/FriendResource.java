package pl.user.friend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.user.UserDto;
import pl.wallet.WalletDto;

import java.security.Principal;

@RestController
@RequestMapping("/user/friend")
public class FriendResource {

  private FriendController friendController;

  @Autowired
  public FriendResource (FriendController friendController) {
    this.friendController = friendController;
  }

  @PostMapping("/add")
  public ResponseEntity<UserDto> addFriend (Principal principal, @RequestBody UserDto userDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(friendController.addFriend(principal, userDto));
  }

  @PostMapping("/{walletId}/add")
  public ResponseEntity<WalletDto> shareWalletWithFriend (Principal principal, @PathVariable Long walletId, @RequestBody UserDto friend) {
    return ResponseEntity.ok(friendController.shareWalletWithFriend(principal, friend, walletId));
  }

  @DeleteMapping("/{walletId}/remove")
  public ResponseEntity<WalletDto> removeFriendFromWallet (Principal principal, @PathVariable Long wallteId, @RequestBody UserDto friend) {
    return ResponseEntity.status(HttpStatus.OK).body(friendController.removeFriendFromWallet(principal, wallteId, friend));
  }
}
