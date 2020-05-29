package pl.wallet.category.default_category;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import pl.wallet.category.Category;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class DefaultCategoryController {
  private DefaultCategoryService defaultCategoryService;

  public List<Category> getDefaultCategories () {
    return this.defaultCategoryService.getDefaultCategories();
  }

  @EventListener(ApplicationReadyEvent.class)
  public void addDefaultCategoryToDb () {
    try {
      defaultCategoryService.setDefaultCategories(
        defaultCategoryService.getDefaultCategoryFromEnum()
          .stream()
          .map(defaultCategoryService::saveCategory)
          .collect(Collectors.toList()));
    } catch (Exception ignored) {
    }
  }


}


