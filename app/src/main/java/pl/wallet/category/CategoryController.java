package pl.wallet.category;

import lombok.AllArgsConstructor;
import pl.user.UserFacade;
import pl.user.UserQueryService;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
class CategoryController {
   private final UserFacade userFacade;

   private final CategoryRepository categoryRepository;
   private final UserQueryService userQueryService;
   private final CategoryQueryRepository categoryQueryRepository;


   CategoryDto addCategory(Principal principal, CategoryDto categoryDto) {
      userQueryService.findByEmail(principal.getName());
      Category category = CategoryMapper.toEntity(categoryDto);
      userFacade.addCategoryToUser(principal.getName(), CategoryMapper.toQueryDto(category));
      userFacade.addUserToCategory(principal.getName(), CategoryMapper.toQueryDto(category));
      return CategoryMapper.toDto(category);
   }

   void removeCategory(Principal principal, Long categoryId) {
      userQueryService.findByEmail(principal.getName());
      userFacade.removeCategoryFromUser(principal.getName(), categoryId);
   }

   Set<CategoryDto> getCategories(Principal principal) {
      userQueryService.findByEmail(principal.getName());
      return categoryQueryRepository.findByUser_Email(principal.getName());
   }

   Set<CategoryDto> restoreDefaultCategories(Principal principal) {
      userQueryService.findByEmail(principal.getName());
      Set<SimpleCategoryQueryDto> defaultCategories = categoryRepository.getDefaultCategories().stream().map(CategoryMapper::toQueryDto).collect(Collectors.toSet());
      userFacade.addCategoriesToUserAndSave(principal.getName(), defaultCategories);
      return getCategories(principal);
   }
}
