package pl.wallet.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.user.User;
import pl.wallet.transaction.Transaction;

import java.util.Set;

@Service
@AllArgsConstructor
public class CategoryFacade {
   private final CategoryRepository categoryRepository;

   public CategoryDto getCategory(User user, Long categoryId) {
      return CategoryMapper.toDto(categoryRepository.findByIdAndUsers(categoryId, user).orElseThrow(() -> new CategoryException(CategoryError.NOT_FOUND)));
   }

   public Set<Category> getDefaultCategories() {
      return categoryRepository.getDefaultCategories();
   }

   public void setCategory(User user, Transaction transaction, Long categoryId) {
      transaction.setCategory(categoryRepository.findByIdAndUsers(categoryId, user).orElseThrow(() -> new CategoryException(CategoryError.NOT_FOUND)));
   }
}
