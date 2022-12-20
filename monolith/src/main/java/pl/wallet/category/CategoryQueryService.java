package pl.wallet.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryQueryService {

   private final CategoryQueryRepository categoryQueryRepository;

   public CategoryDto findByIdAndUserEmail(Long id, String name) {
      return categoryQueryRepository.findByIdAndUserEmail(id, name).orElseThrow(() -> new CategoryException(CategoryError.NOT_FOUND));
   }
}
