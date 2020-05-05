package pl.wallet.category;

import pl.test_tool.RandomTool;
import pl.wallet.transaction.TransactionType;

import java.security.Principal;

public class CategoryTool {

  private static String getRandomName () {
    return RandomTool.getRandomString();
  }

  public static CategoryDto getRandomCategory () {
    return CategoryDto
      .builder()
      .name(getRandomName())
      .transactionType(getRandomTransactionType())
      .build();
  }

  public static CategoryDto.CategoryDtoBuilder getCategoryBuilderWIthNecessaryRandomValue () {
    return CategoryDto
      .builder()
      .name(getRandomName())
      .transactionType(getRandomTransactionType());
  }

  public static TransactionType getRandomTransactionType () {
    return TransactionType.values()[RandomTool.getNumberInteger() % TransactionType.values().length];
  }

  public static CategoryDto saveRandomCategory (Principal principal, CategoryController categoryController) {
    return categoryController.addCategory(principal, getRandomCategory());
  }


  public static CategoryDto saveRandomCategory (Principal principal, CategoryResource categoryResource) {
    return categoryResource.addCategory(principal, getRandomCategory()).getBody();
  }
  public static CategoryDto saveCategory (Principal principal, CategoryDto categoryDto, CategoryController categoryController) {
    return categoryController.addCategory(principal, categoryDto);
  }
}
