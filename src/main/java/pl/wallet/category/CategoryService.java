package pl.wallet.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.exception.ThereIsNoYourPropertyException;
import pl.user.User;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

  private CategoryRepository categoryRepository;

  public Category getCategory (User user, Long categoryId) {
    return categoryRepository.findByIdAndUsers(categoryId, user).orElseThrow(ThereIsNoYourPropertyException::new);
  }

//  public Category getCategory (Long categoryId) {
//    return categoryRepository.getById(categoryId).orElseThrow(() -> new EntityNotFoundException(categoryId, categoryId.getClass()));
//  }

  Category saveCategory (Category category) {
    return categoryRepository.save(category);
  }

  List<Category> getCategoriesByUser (User user) {
    return categoryRepository.findByUsers(user);
  }

  public List<Category> getDefaultCategories () {
    return categoryRepository.getDefaultCategories();
  }
}
