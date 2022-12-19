package pl.wallet.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.user.UserDto;
import pl.user.UserFacade;
import pl.user.UserQueryRepository;
import pl.wallet.transaction.Transaction;

@Service
@AllArgsConstructor
public class CategoryFacade {
   private final CategoryRepository categoryRepository;

   private final UserQueryRepository userQueryRepository;

   private final UserFacade userFacade;

   public Transaction setCategory(String email, Transaction transaction, Long categoryId) {
      return transaction.toBuilder().category(categoryRepository.findByIdAndUserEmail(categoryId, email).orElseThrow(() -> new CategoryException(CategoryError.NOT_FOUND))).build();
   }

   public void addDefaultCategoriesToUser(String email) {
      userQueryRepository.findByEmail(email).orElseThrow(() -> new CategoryException(CategoryError.NOT_FOUND));
      userFacade.addCategoriesToUserAndSave(email, categoryRepository.getDefaultCategories());
   }


}
