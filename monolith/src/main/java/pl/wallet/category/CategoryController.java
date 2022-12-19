package pl.wallet.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import pl.user.*;

import java.security.Principal;
import java.util.Set;

@Controller
@AllArgsConstructor
class CategoryController {
   private final UserFacade userFacade;

   private final CategoryRepository categoryRepository;
   private final UserQueryRepository userQueryRepository;
   private final CategoryQueryRepository categoryQueryRepository;



   CategoryDto addCategory(Principal principal, CategoryDto categoryDto) {
      userQueryRepository.findByEmail(principal.getName()).orElseThrow(() -> new UserException(UserError.NOT_FOUND));
      Category category = CategoryMapper.toEntity(categoryDto);
      userFacade.addCategoryToUser(principal.getName(), category);
      userFacade.addUserToCategory(principal.getName(), category);
      category = categoryRepository.save(category);
      return CategoryMapper.toDto(category);
   }

   void removeCategory(Principal principal, Long categoryId) {
      userQueryRepository.findByEmail(principal.getName()).orElseThrow(() -> new UserException(UserError.NOT_FOUND));
      userFacade.removeCategoryFromUser(principal.getName(), categoryId);
   }

   Set<CategoryDto> getCategories(Principal principal) {
      userQueryRepository.findByEmail(principal.getName()).orElseThrow(() -> new UserException(UserError.NOT_FOUND));
      return categoryQueryRepository.findByUser_Email(principal.getName());
   }

   Set<CategoryDto> restoreDefaultCategories(Principal principal) {
      userQueryRepository.findByEmail(principal.getName()).orElseThrow(() -> new UserException(UserError.NOT_FOUND));
      Set<Category> defaultCategories =  categoryRepository.getDefaultCategories();
      userFacade.addCategoriesToUserAndSave(principal.getName(), defaultCategories);
      return getCategories(principal);
   }
}
