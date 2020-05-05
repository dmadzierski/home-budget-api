package pl.wallet.category.default_category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import pl.wallet.category.CategoryService;

import java.util.stream.Collectors;

@Controller
public class DefaultCategoryController {
  private DefaultCategoryService defaultCategoryService;
  private CategoryService categoryService;

  @Autowired
  public DefaultCategoryController (DefaultCategoryService defaultCategoryService, CategoryService categoryService) {
    this.defaultCategoryService = defaultCategoryService;
    this.categoryService = categoryService;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void addDefaultCategoryToDb () {
    defaultCategoryService.setDefaultCategories(
      defaultCategoryService
        .getDefaultCategoryFromEnum()
        .stream()
        .map(categoryService::saveCategory)
        .collect(Collectors.toList()));
  }
}
