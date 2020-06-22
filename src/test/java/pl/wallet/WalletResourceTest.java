package pl.wallet;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import pl.exception.SavedEntityCanNotHaveIdException;
import pl.exception.ThereIsNoYourPropertyException;
import pl.test_tool.RandomTool;
import pl.test_tool.error.ServerError;
import pl.test_tool.error.WalletError;
import pl.user.UserDto;
import pl.user.UserResource;
import pl.user.UserTool;
import pl.wallet.category.CategoryResource;
import pl.wallet.transaction.TransactionResource;

import javax.validation.ConstraintViolationException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class WalletResourceTest {
  private WalletResource walletResource;
  private UserResource userResource;
  private TransactionResource transactionResource;
  private CategoryResource categoryResource;

  @Autowired
  WalletResourceTest (WalletResource walletResource, UserResource userResource, TransactionResource transactionResource, CategoryResource categoryResource) {
    this.walletResource = walletResource;
    this.userResource = userResource;
    this.transactionResource = transactionResource;
    this.categoryResource = categoryResource;
  }

  @Test
  void should_add_wallet () {
    //given
    UserDto userDto = UserTool.registerRandomUser(userResource);
    Principal principal = userDto::getEmail;
    WalletDto walletDto = WalletTool.randomWallet();
    //when
    ResponseEntity<WalletDto> walletDtoResponseEntity = walletResource.addWallet(principal, walletDto);
    //then
    assertThat(walletDtoResponseEntity).isNotNull();
    assertThat(walletDtoResponseEntity.getBody()).isEqualToIgnoringNullFields(walletDto);
    assertThat(walletDtoResponseEntity.getStatusCode()).isEqualTo(CREATED);
  }

  @Test
  void should_not_add_wallet_with_id () {
    //given
    UserDto userDto = UserTool.registerRandomUser(userResource);
    Principal principal = userDto::getEmail;
    WalletDto walletDto = WalletTool.walletBuilderWithNecessaryValue().id(RandomTool.id()).build();
    //when

    //then
    assertThatExceptionOfType(SavedEntityCanNotHaveIdException.class).isThrownBy(() -> walletResource.addWallet(principal, walletDto))
      .withMessageEndingWith(ServerError.SAVED_ENTITY_CAN_NOT_HAVE_ID.getMessage());
  }

  @Test
  void should_not_add_wallet_without_name () {
    //given
    UserDto userDto = UserTool.registerRandomUser(userResource);
    Principal principal = userDto::getEmail;
    WalletDto walletDto = WalletTool.walletBuilderWithNecessaryValue().name(null).build();
    //when

    //then
    assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() -> walletResource.addWallet(principal, walletDto))
      .withMessageEndingWith(WalletError.NAME_NOT_NULL.getFieldName());
  }

  @Test
  void should_not_add_wallet_without_balance () {
    //given
    UserDto userDto = UserTool.registerRandomUser(userResource);
    Principal principal = userDto::getEmail;
    WalletDto walletDto = WalletTool.walletBuilderWithNecessaryValue().balance(null).build();
    //when

    //then
    assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() -> walletResource.addWallet(principal, walletDto))
      .withMessageEndingWith(WalletError.BALANCE_NOT_NULL.getFieldName());
  }

  @Test
  void should_return_only_user_wallet_by_id () {
    //given
    WalletTool.saveRandomWallet(UserTool.registerRandomUser(userResource)::getEmail, walletResource);
    UserDto userDto = UserTool.registerRandomUser(userResource);
    Principal principal = userDto::getEmail;
    WalletDto walletDto = WalletTool.saveRandomWallet(principal, walletResource);
    WalletDto expectedWalletDto = WalletDto.builder()
      .id(walletDto.getId())
      .name(walletDto.getName())
      .balance(walletDto.getBalance())
      .transactions(null)
      .build();
    //when
    ResponseEntity<WalletDto> walletDtoResponseEntity = walletResource.getWallet(principal, walletDto.getId());
    //then
    assertThat(walletDtoResponseEntity).isNotNull();
    assertThat(walletDtoResponseEntity.getStatusCode()).isEqualTo(OK);
    assertThat(walletDtoResponseEntity.getBody()).isEqualToIgnoringGivenFields(expectedWalletDto, "balance");
    assertThat(walletDtoResponseEntity.getBody().getBalance())
      .isEqualByComparingTo(expectedWalletDto.getBalance());
  }

  @Test
  void can_not_get_wallet_not_on_your_own () {
    //given
    WalletDto walletDto = WalletTool.saveRandomWallet(UserTool.registerRandomUser(userResource)::getEmail, walletResource);

    UserDto userDto = UserTool.registerRandomUser(userResource);
    Principal principal = userDto::getEmail;
    WalletTool.saveRandomWallet(principal, walletResource);
    //when

    //then
    assertThatExceptionOfType(ThereIsNoYourPropertyException.class).isThrownBy(() -> walletResource.getWallet(principal, walletDto.getId()))
      .withMessage(new ThereIsNoYourPropertyException().getMessage());
  }

  @Test
  void user_can_remove_own_wallet () {
    //given
    UserDto userDto = UserTool.registerRandomUser(userResource);
    Principal principal = userDto::getEmail;
    WalletDto walletDto = WalletTool.randomWallet();
    Long walletId = walletResource.addWallet(principal, walletDto).getBody().getId();
    //when
    ResponseEntity entity = walletResource.removeWallet(principal, walletId);
    //then
    Assertions.assertThat(entity).isNotNull();
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    assertThat(entity.getBody()).isNull();
  }

  @Test
  void user_can_not_remove_someones_wallet () {
    //given
    UserDto userDto = UserTool.registerRandomUser(userResource);
    Principal principal = userDto::getEmail;
    Long walletId = addWallets(UserTool.registerRandomUser(userResource)::getEmail, 1).get(0).getId();
    //when

    //then
    assertThatExceptionOfType(ThereIsNoYourPropertyException.class)
      .isThrownBy(() -> walletResource.removeWallet(principal, walletId))
      .withMessage(new ThereIsNoYourPropertyException().getMessage());
  }

  private List<WalletDto> addWallets (Principal principal, int number) {
    List<WalletDto> wallets = new ArrayList<>();
    for (int i = 0; i < number; i++) {
      wallets.add(WalletTool.saveRandomWallet(principal, walletResource));
    }
    return wallets;
  }
}
