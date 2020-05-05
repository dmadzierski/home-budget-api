package pl.wallet;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import pl.user.UserController;
import pl.user.UserDto;
import pl.user.UserTool;

import java.security.Principal;
import java.util.Collections;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class WalletControllerTest {

  private UserController userController;
  private WalletController walletController;

  @Autowired
  WalletControllerTest (UserController userController, WalletController walletController) {
    this.userController = userController;
    this.walletController = walletController;
  }

  @Test
  void should_add_wallet () {
    //given
    UserDto userDto = UserTool.registerRandomUser(this.userController);
    Principal principal = userDto::getEmail;
    WalletDto walletDto = WalletTool.randomWallet();
    WalletDto expectedWalletDto = WalletDto.builder()
      .id(1L)
      .balance(walletDto.getBalance())
      .name(walletDto.getName())
      .users(Collections.singletonList(userDto))
      .build();
    //when
    WalletDto savedWallet = walletController.addWallet(principal, walletDto);
    //then
    System.out.println(savedWallet);
    System.out.println(expectedWalletDto);
    Assertions.assertThat(savedWallet).isEqualToComparingFieldByField(expectedWalletDto);
  }
}
