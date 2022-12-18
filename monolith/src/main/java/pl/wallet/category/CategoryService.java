package pl.wallet.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.user.User;

import java.util.Set;

@Service
@AllArgsConstructor
class CategoryService {

   private final CategoryRepository categoryRepository;
   private final CategoryQueryRepository categoryQueryRepository;


   Category saveCategory(Category category) {
      return categoryRepository.save(category);
   }

   Set<Category> getCategoriesByUser(User user) {
      return categoryQueryRepository.findByUsers(user);
   }

   public Set<Category> getDefaultCategories() {
      return categoryQueryRepository.getDefaultCategories();
   }

}
