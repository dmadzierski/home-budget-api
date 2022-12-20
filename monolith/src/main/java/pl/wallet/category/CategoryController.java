package pl.wallet.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import pl.user.UserError;
import pl.user.UserException;
import pl.user.UserFacade;
import pl.user.UserQueryRepository;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

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
      userFacade.addCategoryToUser(principal.getName(), CategoryMapper.toQueryDto(category));
      userFacade.addUserToCategory(principal.getName(), CategoryMapper.toQueryDto(category));
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
      Set<SimpleCategoryQueryDto> defaultCategories = categoryRepository.getDefaultCategories().stream().map(CategoryMapper::toQueryDto).collect(Collectors.toSet());
      userFacade.addCategoriesToUserAndSave(principal.getName(), defaultCategories);
      return getCategories(principal);
   }
}
