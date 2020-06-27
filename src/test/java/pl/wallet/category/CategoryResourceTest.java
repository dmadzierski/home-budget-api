package pl.wallet.category;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import pl.user.UserDto;
import pl.user.UserResource;
import pl.user.UserTool;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class CategoryResourceTest {
  private CategoryResource categoryResource;
  private UserResource userResource;

  @Autowired
  public CategoryResourceTest (CategoryResource categoryResource, UserResource userResource) {
    this.categoryResource = categoryResource;
    this.userResource = userResource;
  }

  @Test
  void should_add_category () {
    //given
    UserDto userDto = UserTool.registerRandomUser(userResource);
    CategoryDto categoryDto = CategoryTool.getRandomCategory();
    Principal principal = userDto::getEmail;
    CategoryDto expectedBody = categoryDto;
    //when
    ResponseEntity<CategoryDto> categoryDtoResponseEntity = categoryResource.addCategory(principal, categoryDto);
    //then
    assertThat(categoryDtoResponseEntity).isNotNull();
    assertThat(categoryDtoResponseEntity).extracting(ResponseEntity::getStatusCode).isEqualTo(HttpStatus.CREATED);
    assertThat(categoryDtoResponseEntity).extracting(ResponseEntity::getBody).isEqualToComparingOnlyGivenFields(expectedBody);
    assertThat(categoryDtoResponseEntity).extracting(ResponseEntity::getBody).extracting(CategoryDto::getId).isNotNull();
    assertThat(categoryDtoResponseEntity).extracting(ResponseEntity::getBody).extracting(CategoryDto::getId).isNotNull();
  }

  @Test
  void should_remove_category () {
    //given
    UserDto userDto = UserTool.registerRandomUser(userResource);
    CategoryDto categoryDto = CategoryTool.getRandomCategory();
    Principal principal = userDto::getEmail;
    Long categoryId = categoryResource.addCategory(principal, categoryDto).getBody().getId();
    //when
    ResponseEntity entity = categoryResource.removeCategory(principal, categoryId);
    //then
    assertThat(entity).isNotNull();
    assertThat(entity.getBody()).isNull();
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  void should_return_categories () {
    //given
    int number = 100;
    UserDto userDto = UserTool.registerRandomUser(userResource);
    CategoryDto categoryDto = CategoryTool.getRandomCategory();
    Principal principal = userDto::getEmail;
    List<CategoryDto> categoriesDto = addCategories(number, principal);
    //when
    ResponseEntity<Set<CategoryDto>> categoriesDtoResponseEntity = categoryResource.getUserCategories(principal);
    //then
    assertThat(categoriesDtoResponseEntity).isNotNull();
    assertThat(categoriesDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(categoriesDtoResponseEntity.getBody()).hasSize(number + 8);

  }

  private List<CategoryDto> addCategories (int number, Principal principal) {
    return IntStream.iterate(0, i -> i + 1).limit(number).mapToObj(k -> CategoryTool.saveRandomCategory(principal, categoryResource)).collect(Collectors.toList());
  }
}
