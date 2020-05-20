package pl.wallet.category.default_category;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import pl.wallet.category.CategoryService;

import java.util.stream.Collectors;

@Controller
@AllArgsConstructor

public class DefaultCategoryController {
  private DefaultCategoryService defaultCategoryService;
  private CategoryService categoryService;


  @EventListener(ApplicationReadyEvent.class)
  public void addDefaultCategoryToDb () {
    try {
      defaultCategoryService.setDefaultCategories(
        defaultCategoryService
          .getDefaultCategoryFromEnum()
          .stream()
          .map(categoryService::saveCategory)
          .collect(Collectors.toList()));
    } catch (Exception ignored) {
    }
  }
}
