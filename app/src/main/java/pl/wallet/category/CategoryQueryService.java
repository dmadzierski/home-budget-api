package pl.wallet.category;

import org.springframework.stereotype.Service;

@Service
public class CategoryQueryService {

   private final CategoryQueryService categoryQueryService;

   public CategoryDto findByIdAndUserEmail(Long id, String name) {
      return categoryQueryService.findByIdAndUserEmail(id, name);
   }
}
