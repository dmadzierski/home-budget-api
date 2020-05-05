package pl.wallet;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import pl.exception.ThereIsNoYourPropertyException;
import pl.test_tool.RandomTool;
import pl.test_tool.error.WalletError;
import pl.user.RegisterResource;
import pl.user.UserDto;
import pl.user.UserTool;
import pl.wallet.category.CategoryDto;
import pl.wallet.category.CategoryResource;
import pl.wallet.category.CategoryTool;
import pl.wallet.transaction.TransactionResource;
import pl.wallet.transaction.TransactionTool;

import javax.validation.ConstraintViolationException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class WalletResourceTest {
  private WalletResource walletResource;
  private RegisterResource registerResource;
  private TransactionResource transactionResource;
  private CategoryResource categoryResource;

  @Autowired
  WalletResourceTest (WalletResource walletResource, RegisterResource registerResource, TransactionResource transactionResource, CategoryResource categoryResource) {
    this.walletResource = walletResource;
    this.registerResource = registerResource;
    this.transactionResource = transactionResource;
    this.categoryResource = categoryResource;
  }

  @Test
  void should_add_wallet () {
    //given
    UserDto userDto = UserTool.registerRandomUser(registerResource);
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
    UserDto userDto = UserTool.registerRandomUser(registerResource);
    Principal principal = userDto::getEmail;
    WalletDto walletDto = WalletTool.walletBuilderWithNecessaryValue().id(RandomTool.id()).build();
    //when

    //then
    assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() -> walletResource.addWallet(principal, walletDto))
      .withMessageEndingWith(WalletError.ID_NULL.getFieldName());
  }

  @Test
  void should_not_add_wallet_without_name () {
    //given
    UserDto userDto = UserTool.registerRandomUser(registerResource);
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
    UserDto userDto = UserTool.registerRandomUser(registerResource);
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
    WalletTool.saveRandomWallet(UserTool.registerRandomUser(registerResource)::getEmail, walletResource);
    UserDto userDto = UserTool.registerRandomUser(registerResource);
    Principal principal = userDto::getEmail;
    WalletDto walletDto = WalletTool.saveRandomWallet(principal, walletResource);
    WalletDto expectedWalletDto = WalletDto.builder()
      .id(walletDto.getId())
      .name(walletDto.getName())
      .balance(walletDto.getBalance())
      .transactions(new ArrayList<>())
      .users(Collections.singletonList(userDto))
      .build();
    //when
    ResponseEntity<WalletDto> walletDtoResponseEntity = walletResource.getWallet(principal, walletDto.getId());
    //then
    assertThat(walletDtoResponseEntity).isNotNull();
    assertThat(walletDtoResponseEntity.getStatusCode()).isEqualTo(OK);
    assertThat(walletDtoResponseEntity.getBody()).isEqualTo(expectedWalletDto);
  }

  @Test
  void can_not_get_wallet_not_on_your_own () {
    //given
    WalletDto walletDto = WalletTool.saveRandomWallet(UserTool.registerRandomUser(registerResource)::getEmail, walletResource);

    UserDto userDto = UserTool.registerRandomUser(registerResource);
    Principal principal = userDto::getEmail;
    WalletTool.saveRandomWallet(principal, walletResource);
    //when

    //then
    assertThatExceptionOfType(ThereIsNoYourPropertyException.class).isThrownBy(() -> walletResource.getWallet(principal, walletDto.getId()))
      .withMessage(new ThereIsNoYourPropertyException().getMessage());
  }

  @Test
  void user_can_get_all_own_wallets () {
    //given
    UserDto userDto = UserTool.registerRandomUser(registerResource);
    Principal principal = userDto::getEmail;
    int numberOfWallets = 100;
    List<WalletDto> wallets = addWallets(principal, numberOfWallets);
    //when
    ResponseEntity<List<WalletDto>> walletsResponseEntity = walletResource.getWallets(principal);
    //then
    assertThat(walletsResponseEntity).isNotNull();
    assertThat(walletsResponseEntity.getStatusCode()).isEqualTo(OK);
    assertThat(walletsResponseEntity.getBody()).hasSize(numberOfWallets)
      .allMatch(Objects::nonNull)
      .allMatch(k -> wallets.stream().anyMatch(l -> l.equals(k)));
  }

  @Test
  void user_can_remove_own_wallet () {
    //given
    UserDto userDto = UserTool.registerRandomUser(registerResource);
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
    UserDto userDto = UserTool.registerRandomUser(registerResource);
    Principal principal = userDto::getEmail;
    Long walletId = addWallets(UserTool.registerRandomUser(registerResource)::getEmail, 1).get(0).getId();
    //when

    //then
    assertThatExceptionOfType(ThereIsNoYourPropertyException.class)
      .isThrownBy(() -> walletResource.removeWallet(principal, walletId))
      .withMessage(new ThereIsNoYourPropertyException().getMessage());
  }

  @Test
  void user_can_get_wallet_with_transaction () {
    //given
    UserDto userDto = UserTool.registerRandomUser(registerResource);
    Principal principal = userDto::getEmail;
    WalletDto walletDto = WalletTool.saveRandomWallet(principal, walletResource);
    CategoryDto categoryDto = CategoryTool.saveRandomCategory(principal, categoryResource);
    TransactionTool.saveRandomTransaction(principal, walletDto, categoryDto, transactionResource);
    //when
    ResponseEntity<WalletDto> wallet = walletResource.getWallet(principal, walletDto.getId());
    //then
    System.out.println(wallet.getBody().getTransactions().size());
  }

  private List<WalletDto> addWallets (Principal principal, int number) {
    List<WalletDto> wallets = new ArrayList<>();
    for (int i = 0; i < number; i++) {
      wallets.add(WalletTool.saveRandomWallet(principal, walletResource));
    }
    return wallets;
  }
}
