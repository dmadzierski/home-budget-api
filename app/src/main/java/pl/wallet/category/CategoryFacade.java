package pl.wallet.category;

import lombok.AllArgsConstructor;
import pl.user.UserFacade;
import pl.user.UserQueryService;
import pl.wallet.transaction.SimpleTransactionQueryDto;

import java.util.stream.Collectors;

@AllArgsConstructor
public class CategoryFacade {
   private final CategoryRepository categoryRepository;
   private final UserQueryService userQueryService;

   private final UserFacade userFacade;


   public SimpleCategoryQueryDto setCategory(String email, SimpleTransactionQueryDto transaction, Long categoryId) {
      return CategoryMapper.toQueryDto(categoryRepository.findByIdAndUserEmail(categoryId, email).orElseThrow(() -> new CategoryException(CategoryError.NOT_FOUND)));
   }

   public void addDefaultCategoriesToUser(String email) {
      userQueryService.findByEmail(email);
      userFacade.addCategoriesToUserAndSave(email, categoryRepository.getDefaultCategories().stream().map(CategoryMapper::toQueryDto).collect(Collectors.toSet()));
   }


}
