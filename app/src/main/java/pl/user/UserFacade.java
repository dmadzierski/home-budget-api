package pl.user;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pl.wallet.Wallet;
import pl.wallet.category.SimpleCategoryQueryDto;
import pl.wallet.transaction.SimpleTransactionQueryDto;

import java.util.Set;

@Service
@AllArgsConstructor
public class UserFacade {
   private final UserRepository userRepository;


   public void addFilterByUser(Specification<SimpleTransactionQueryDto> transactionSpecification, String name) {
      User user = userRepository.findByEmail(name).orElseThrow(() -> new UserException(UserError.NOT_FOUND));
      transactionSpecification.and(new UserWallet(user));
   }

   public Wallet addUserToWallet(Wallet wallet, String name) {
      return wallet.toBuilder().user(UserMapper.toQueryDto(userRepository.findByEmail(name).orElseThrow(() -> new UserException(UserError.NOT_FOUND)))).build();

   }

   public void addCategoryToUser(String email, SimpleCategoryQueryDto simpleCategoryQueryDto) {
      userRepository.findByEmail(email).orElseThrow(() -> new UserException(UserError.NOT_FOUND)).addCategory(simpleCategoryQueryDto);
   }

   public void addUserToCategory(String email, SimpleCategoryQueryDto simpleCategoryQueryDto) {
      User user = userRepository.findByEmail(email).orElseThrow(() -> new UserException(UserError.NOT_FOUND));
      simpleCategoryQueryDto.addUser(UserMapper.toQueryDto(user));
   }

   public void removeCategoryFromUser(String email, Long categoryId) {
      User user = userRepository.findByEmail(email).orElseThrow(() -> new UserException(UserError.NOT_FOUND));
      user.removeCategory(categoryId);
      userRepository.save(user);
   }

   public void addCategoriesToUserAndSave(String email, Set<SimpleCategoryQueryDto> defaultCategories) {
      User user = userRepository.findByEmail(email).orElseThrow(() -> new UserException(UserError.NOT_FOUND));
      defaultCategories.forEach(user::addCategory);
      userRepository.save(user);
   }
}
