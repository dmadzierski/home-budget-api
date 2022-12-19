package pl.user;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pl.wallet.Wallet;
import pl.wallet.category.Category;
import pl.wallet.transaction.Transaction;

import java.util.Set;

@Service
@AllArgsConstructor
public class UserFacade {
   private final UserRepository userRepository;


   public User saveUser(User user) {
      return userRepository.save(user);
   }

   public void addFilterByUser(Specification<Transaction> transactionSpecification, String name) {
      User user = userRepository.findByEmail(name).orElseThrow(() -> new UserException(UserError.NOT_FOUND));
      transactionSpecification.and(new UserWallet(user));
   }

   public Wallet addUserToWallet(Wallet wallet, String name) {
      return wallet.toBuilder().user(userRepository.findByEmail(name).orElseThrow(() -> new UserException(UserError.NOT_FOUND))).build();

   }

   public void addCategoryToUser(String email, Category category) {
      userRepository.findByEmail(email).orElseThrow(() -> new UserException(UserError.NOT_FOUND)).addCategory(category);
   }

   public void addUserToCategory(String email, Category category) {
      User user = userRepository.findByEmail(email).orElseThrow(() -> new UserException(UserError.NOT_FOUND));
      category.addUser(user);
   }

   public void removeCategoryFromUser(String email, Long categoryId) {
      User user = userRepository.findByEmail(email).orElseThrow(() -> new UserException(UserError.NOT_FOUND));
      user.removeCategory(categoryId);
      userRepository.save(user);
   }

   public void addCategoriesToUserAndSave(String email, Set<Category> defaultCategories) {
      User user = userRepository.findByEmail(email).orElseThrow(() -> new UserException(UserError.NOT_FOUND));
      defaultCategories.forEach(user::addCategory);
      userRepository.save(user);
   }
}
