package pl.wallet.transaction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import pl.exception.SavedEntityCanNotHaveIdException;
import pl.test_tool.RandomTool;
import pl.test_tool.error.TransactionError;
import pl.user.UserController;
import pl.user.UserDto;
import pl.user.UserTool;
import pl.wallet.WalletController;
import pl.wallet.WalletDto;
import pl.wallet.WalletResource;
import pl.wallet.WalletTool;
import pl.wallet.category.CategoryController;
import pl.wallet.category.CategoryDto;
import pl.wallet.category.CategoryTool;

import javax.validation.ConstraintViolationException;
import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.springframework.http.HttpStatus.CREATED;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class TransactionResourceTest {

  private WalletController walletController;
  private UserController userController;
  private CategoryController categoryController;
  private TransactionResource transactionResource;
  private WalletResource walletResource;

  @Autowired
  public TransactionResourceTest (WalletController walletController, UserController userController, CategoryController categoryController, TransactionResource transactionResource, WalletResource walletResource) {
    this.walletController = walletController;
    this.userController = userController;
    this.categoryController = categoryController;
    this.transactionResource = transactionResource;
    this.walletResource = walletResource;
  }


  @Test
  void new_transaction_should_not_have_id () {
    //given
    UserDto userDto = UserTool.registerRandomUser(userController);
    Principal principal = userDto::getEmail;
    WalletDto walletDto = WalletTool.saveRandomWallet(principal, walletController);
    CategoryDto categoryDto = CategoryTool.saveRandomCategory(principal, categoryController);
    TransactionDto transactionDto = TransactionTool.getTransactionBuilderWithNecessaryValue().id(RandomTool.id()).build();
    //when

    //then
    assertThatExceptionOfType(SavedEntityCanNotHaveIdException.class).isThrownBy(
      () -> transactionResource.addTransaction(principal, categoryDto.getId(), walletDto.getId(), transactionDto));
  }

  @Test
  void should_add_new_transaction () {
    //given
    UserDto userDto = UserTool.registerRandomUser(userController);
    Principal principal = userDto::getEmail;
    WalletDto walletDto = WalletTool.saveRandomWallet(principal, walletController);
    CategoryDto categoryDto = CategoryTool.saveRandomCategory(principal, categoryController);
    TransactionDto transactionDto = TransactionTool.getRandomTransaction();
    //when
    ResponseEntity<TransactionDto> transactionDtoResponseEntity = transactionResource.addTransaction(principal, categoryDto.getId(), walletDto.getId(), transactionDto);
    //then
    assertThat(transactionDtoResponseEntity).isNotNull();
    assertThat(transactionDtoResponseEntity.getBody()).isEqualToIgnoringNullFields(transactionDto).hasFieldOrProperty("id");
    assertThat(transactionDtoResponseEntity.getStatusCode()).isEqualTo(CREATED);

  }

  @Test
  void should_not_add_new_transaction_without_name () {
    //given
    UserDto userDto = UserTool.registerRandomUser(userController);
    Principal principal = userDto::getEmail;
    WalletDto walletDto = WalletTool.saveRandomWallet(principal, walletController);
    CategoryDto categoryDto = CategoryTool.saveRandomCategory(principal, categoryController);
    TransactionDto transactionDto = TransactionTool.getTransactionBuilderWithNecessaryValue().name(null).build();
    //when

    //then
    assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(
      () -> transactionResource.addTransaction(principal, categoryDto.getId(), walletDto.getId(), transactionDto))
      .withMessageEndingWith(TransactionError.NAME_NOT_NULL.getMessage());
  }


}
