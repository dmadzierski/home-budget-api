package pl.wallet.category;

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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CategoryControllerTest {

  private CategoryController categoryController;
  private UserController userController;

  @Autowired
  public CategoryControllerTest (CategoryController categoryController, UserController userController) {
    this.categoryController = categoryController;
    this.userController = userController;
  }

  @Test
  void should_add_category () {
    //given
    UserDto userDto = UserTool.registerRandomUser(userController);
    Principal principal = userDto::getEmail;
    CategoryDto categoryDto = CategoryTool.getRandomCategory();
    CategoryDto expectedCategoryDto = categoryDto;
    //when

    //then
    Assertions.assertThat(categoryController.addCategory(principal, categoryDto)).isEqualToIgnoringNullFields(categoryDto).extracting(CategoryDto::getId).isNotNull();
  }

}
