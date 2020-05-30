package pl.user.friend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import pl.exception.CanNotNotAddOldFriendException;
import pl.user.UserDto;
import pl.user.UserResource;
import pl.user.UserTool;
import pl.wallet.WalletController;
import pl.wallet.WalletDto;
import pl.wallet.WalletTool;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FriendControllerTest {

  private FriendController friendController;
  private WalletController walletController;
  private UserResource userResource;

  @Autowired
  FriendControllerTest (FriendController friendController, WalletController walletController, UserResource userResource) {
    this.friendController = friendController;
    this.walletController = walletController;
    this.userResource = userResource;
  }


  @Test
  void should_add_new_friend () {
    //given
    UserDto userDto = UserTool.registerRandomUser(userResource);
    UserDto friend = UserTool.registerRandomUser(userResource);
    Principal principal = userDto::getEmail;
    UserDto expectedUserDto = UserDto.builder()
      .email(userDto.getEmail())
      .friends(Collections.singletonList(FriendDto.builder().name(friend.getEmail()).build()))
      .build();
    //when
    UserDto userDtoWithFriend = friendController.addFriend(principal, friend);
    //then
    assertThat(userDtoWithFriend).isNotNull();
    assertThat(userDtoWithFriend.getFriends()).isNotEmpty();
    assertThat(userDtoWithFriend).isEqualToComparingOnlyGivenFields(expectedUserDto);
    assertThat(userDtoWithFriend.getFriends()).extracting(FriendDto::getDateOfMakingFriend).isNotNull();

  }


  @Test
  void should_not_add_old_friend () {
    //given
    UserDto userDto = UserTool.registerRandomUser(userResource);
    UserDto friend = UserTool.registerRandomUser(userResource);
    Principal principal = userDto::getEmail;
    //when
    friendController.addFriend(principal, friend);
    //then
    try {
      friendController.addFriend(principal, friend);
    } catch (Exception e) {
      assertThat(e).isInstanceOf(CanNotNotAddOldFriendException.class);
    }
  }

  @Test
  void should_share_wallet_with_friends () {
    //given
    UserDto userDto = UserTool.registerRandomUser(userResource);
    UserDto friendDto = UserTool.registerRandomUser(userResource);
    Principal principal = userDto::getEmail;
    WalletDto walletDto = WalletTool.saveRandomWallet(principal, this.walletController);
    WalletDto expectedWallet = WalletDto.builder()
      .id(walletDto.getId())
      .users(Arrays.asList(userDto, friendDto))
      .name(walletDto.getName())
      .balance(walletDto.getBalance())
      .build();
    //when
    WalletDto walletDtoWithAddedFriend = friendController.shareWalletWithFriend(principal, friendDto, walletDto.getId());
    //then
    assertThat(walletDtoWithAddedFriend).isEqualToIgnoringGivenFields(expectedWallet,"balance");
    assertThat(walletDtoWithAddedFriend.getBalance()).isEqualByComparingTo(expectedWallet.getBalance());
  }

}
