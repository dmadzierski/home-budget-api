package pl.user.friend;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import pl.exception.ThereIsNoYourPropertyException;
import pl.exception.WalletAlreadyIsPropertyOfYourFriend;
import pl.user.User;
import pl.user.UserDto;
import pl.user.UserMapper;
import pl.user.UserService;
import pl.wallet.WalletDto;
import pl.wallet.WalletMapper;
import pl.wallet.WalletService;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class FriendController {

  private FriendService friendService;
  private UserService userService;
  private WalletService walletService;


  UserDto addFriend (Principal principal, UserDto friendDto) {
    User user = userService.getUserByPrincipal(principal);
    User friend = userService.getUserByEmail(friendDto.getEmail());
    friendService.areTheyAlreadyFriends(user, friend);
    Friend friendConnect = new Friend();
    friendConnect.setUser(user);
    friendConnect.setFriend(friend);
    friendService.saveFriend(user, friend);
    List<Friend> friends = friendService.getFriendByUser(user);
    return UserMapper.toDtoWithFriend(user, friends);
  }

  WalletDto shareWalletWithFriend (Principal principal, UserDto friendDto, Long walletId) {
    User user = userService.getUserByPrincipal(principal);
    User friend = userService.getUserByEmail(friendDto.getEmail());
    try {
      walletService.isUserWallet(friend, walletId);
    } catch (ThereIsNoYourPropertyException e) {
      walletService.isUserWallet(user, walletId);
      return WalletMapper.toDto(walletService.addFriendToWallet(friend, walletId));
    }
    throw new WalletAlreadyIsPropertyOfYourFriend("This wallet already belongs to your friend");
  }

  WalletDto removeFriendFromWallet (Principal principal, Long walletId, UserDto friendDto) {
    User user = userService.getUserByPrincipal(principal);
    User friend = userService.getUserByEmail(friendDto.getEmail());

    walletService.isUserWallet(friend, walletId);
    walletService.isUserWallet(user, walletId);

    walletService.removeFriendFromWallet(friend, walletId);

    return WalletMapper.toDto(walletService.addFriendToWallet(friend, walletId));
  }
}
