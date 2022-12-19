package pl.wallet.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@AllArgsConstructor
@Service
public class CategoryService {

   CategoryRepository categoryRepository;

   public Category saveCategory(Category category) {
      return categoryRepository.save(category);
   }

   public Set<Category> getDefaultCategories() {
      return categoryRepository.getDefaultCategories();
   }
}
