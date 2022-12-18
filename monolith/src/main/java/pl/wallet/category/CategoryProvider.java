package pl.wallet.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.user.User;

import java.util.Set;

@Service
@AllArgsConstructor
public class CategoryProvider {
   private final CategoryRepository categoryRepository;

   public Category getCategory(User user, Long categoryId) {
      return categoryRepository.findByIdAndUsers(categoryId, user).orElseThrow(() -> new CategoryException(CategoryError.NOT_FOUND));
   }

   public Set<Category> getDefaultCategories() {
      return categoryRepository.getDefaultCategories();
   }

   Category saveCategory(Category category) {
      return categoryRepository.save(category);
   }

   Set<Category> getCategoriesByUser(User user) {
      return categoryRepository.findByUsers(user);
   }

}
