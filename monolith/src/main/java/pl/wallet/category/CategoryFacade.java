package pl.wallet.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.user.User;
import pl.wallet.transaction.Transaction;

@Service
@AllArgsConstructor
public class CategoryFacade {
   private final CategoryQueryRepository categoryQueryRepository;

   public CategoryDto getCategory(User user, Long categoryId) {
      return CategoryMapper.toDto(categoryQueryRepository.findByIdAndUsers(categoryId, user).orElseThrow(() -> new CategoryException(CategoryError.NOT_FOUND)));
   }

   public Transaction setCategory(User user, Transaction transaction, Long categoryId) {
      return transaction.toBuilder().category(categoryQueryRepository.findByIdAndUsers(categoryId, user).orElseThrow(() -> new CategoryException(CategoryError.NOT_FOUND))).build();
   }

   public void addDefaultCategoriesToUser(User user) {
      categoryQueryRepository.getDefaultCategories().forEach(user::addCategory);
   }
}
