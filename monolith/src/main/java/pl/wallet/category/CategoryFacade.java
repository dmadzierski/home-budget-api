package pl.wallet.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.user.UserFacade;
import pl.user.UserQueryRepository;
import pl.wallet.transaction.SimpleTransactionQueryDto;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryFacade {
   private final CategoryRepository categoryRepository;

   private final UserQueryRepository userQueryRepository;

   private final UserFacade userFacade;

   public SimpleCategoryQueryDto setCategory(String email, SimpleTransactionQueryDto transaction, Long categoryId) {
      return CategoryMapper.toQueryDto(categoryRepository.findByIdAndUserEmail(categoryId, email).orElseThrow(() -> new CategoryException(CategoryError.NOT_FOUND)));
   }

   public void addDefaultCategoriesToUser(String email) {
      userQueryRepository.findByEmail(email).orElseThrow(() -> new CategoryException(CategoryError.NOT_FOUND));
      userFacade.addCategoriesToUserAndSave(email, categoryRepository.getDefaultCategories().stream().map(CategoryMapper::toQueryDto).collect(Collectors.toSet()));
   }


}
