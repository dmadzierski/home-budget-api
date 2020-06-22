package pl.wallet.transaction;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import pl.exception.ThereIsNoYourPropertyException;
import pl.user.UserController;
import pl.user.UserDto;
import pl.user.UserTool;
import pl.wallet.WalletController;
import pl.wallet.WalletDto;
import pl.wallet.WalletTool;
import pl.wallet.category.CategoryController;
import pl.wallet.category.CategoryDto;
import pl.wallet.category.CategoryTool;

import java.security.Principal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class TransactionControllerTest {

  private TransactionController transactionController;
  private WalletController walletController;
  private UserController userController;
  private CategoryController categoryController;

  @Autowired
  TransactionControllerTest (TransactionController transactionController, WalletController walletController, UserController userController, CategoryController categoryController) {
    this.transactionController = transactionController;
    this.walletController = walletController;
    this.userController = userController;
    this.categoryController = categoryController;
  }

  @Test
  void should_add_transaction () {
    //given
    UserDto userDto = UserTool.registerRandomUser(userController);
    Principal principal = userDto::getEmail;
    WalletDto walletDto = WalletTool.saveRandomWallet(principal, walletController);
    CategoryDto categoryDto = CategoryTool.saveRandomCategory(principal, categoryController);
    TransactionDto transactionDto = TransactionTool.getRandomTransaction();
    //when
    TransactionDto savedTransactionDto = transactionController.addTransaction(principal, walletDto.getId(), categoryDto.getId(), transactionDto);
    //then
    Assertions.assertThat(savedTransactionDto).isEqualToComparingFieldByField(savedTransactionDto);
  }

  @Test
  void should_not_add_transaction_with_not_your_own_category () {
    UserDto userDto = UserTool.registerRandomUser(userController);
    UserDto userDto2 = UserTool.registerRandomUser(userController);
    Principal principal = userDto::getEmail;
    WalletDto walletDto = WalletTool.saveRandomWallet(principal, walletController);
    CategoryDto categoryDto = CategoryTool.saveRandomCategory(userDto2::getEmail, categoryController);
    TransactionDto transactionDto = TransactionTool.getRandomTransaction();
    //when

    //then
    Assertions.assertThatExceptionOfType(ThereIsNoYourPropertyException.class).isThrownBy(
      () -> transactionController.addTransaction(principal, walletDto.getId(), categoryDto.getId(), transactionDto));
  }

  @Test
  void should_not_add_transaction_to_not_your_property_wallet () {
    UserDto userDto = UserTool.registerRandomUser(userController);
    UserDto userDto2 = UserTool.registerRandomUser(userController);
    Principal principal = userDto::getEmail;

    WalletDto walletDto = WalletTool.saveRandomWallet(userDto2::getEmail, walletController);
    CategoryDto categoryDto = CategoryTool.saveRandomCategory(principal, categoryController);
    TransactionDto transactionDto = TransactionTool.getRandomTransaction();
    //when

    //then
    Assertions.assertThatExceptionOfType(ThereIsNoYourPropertyException.class).isThrownBy(
      () -> transactionController.addTransaction(principal, walletDto.getId(), categoryDto.getId(), transactionDto));
  }


}
