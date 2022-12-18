package pl.wallet.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import pl.user.User;
import pl.user.UserFacade;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
class CategoryController {
   private final CategoryService categoryService;
   private final UserFacade userFacade;

   CategoryDto addCategory(Principal principal, CategoryDto categoryDto) {
      User user = userFacade.getUser(principal);
      Category category = CategoryMapper.toEntity(categoryDto);
      user.addCategory(category);
      category.addUser(user);
      category = categoryService.saveCategory(category);
      return CategoryMapper.toDto(category);
   }

   void removeCategory(Principal principal, Long categoryId) {
      User user = userFacade.getUserByEmail(principal.getName());
      user.removeCategory(categoryId);
      userFacade.saveUser(user);
   }

   Set<CategoryDto> getCategories(Principal principal) {
      User user = userFacade.getUserByEmail(principal.getName());
      return categoryService.getCategoriesByUser(user).stream().map(CategoryMapper::toDto).collect(Collectors.toSet());
   }

   public Set<CategoryDto> restoreDefaultCategories(Principal principal) {
      User user = userFacade.getUserByEmail(principal.getName());
      Set<Category> defaultCategories = categoryService.getDefaultCategories();
      user.setCategories(defaultCategories);
      this.userFacade.saveUser(user);
      return getCategories(principal);
   }
}
