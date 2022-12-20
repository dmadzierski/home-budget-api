package pl.wallet.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryQueryService {

   private final CategoryQueryService categoryQueryService;

   public CategoryDto findByIdAndUserEmail(Long id, String name) {
      return categoryQueryService.findByIdAndUserEmail(id, name);
   }
}
