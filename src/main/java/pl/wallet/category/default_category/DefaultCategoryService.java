package pl.wallet.category.default_category;

import org.springframework.stereotype.Service;
import pl.wallet.category.Category;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultCategoryService {
  private List<Category> defaultCategories;

  List<Category> getDefaultCategoryFromEnum () {
    return Arrays.stream(DefaultCategoriesType.values()).map(DefaultCategoriesType::getCategory).collect(Collectors.toList());
  }

  public List<Category> getDefaultCategories () {
    return defaultCategories;
  }

  void setDefaultCategories (List<Category> defaultCategories) {
    this.defaultCategories = defaultCategories;
  }
}
