package pl.wallet.category.default_category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.user.User;
import pl.wallet.category.Category;
import pl.wallet.category.CategoryRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DefaultCategoryService {

  private CategoryRepository categoryRepository;
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

  public void addDefaultCategories (User user) {
    List<Category> categories = this.defaultCategories;
    categories.forEach(category -> category.addUser(user));
    categories.forEach(this::saveCategory);
  }

  public Category saveCategory (Category category) {
    return categoryRepository.save(category);
  }
}
