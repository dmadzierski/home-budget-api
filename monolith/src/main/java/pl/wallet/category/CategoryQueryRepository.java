package pl.wallet.category;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.user.User;

import java.util.Optional;
import java.util.Set;

public interface CategoryQueryRepository extends org.springframework.data.repository.Repository<Category, Long> {

   @Query("SELECT c FROM Category c JOIN c.users u WHERE u = :user")
   Set<Category> findByUsers(@Param("user") User user);

   @Query("SELECT c FROM Category c WHERE c.isDefault = true")
   Set<Category> getDefaultCategories();

   @Query("SELECT c FROM Category c INNER JOIN c.users u WHERE :categoryId = c.id AND u = :user")
   Optional<Category> findByIdAndUsers(@Param("categoryId") Long categoryId, @Param("user") User user);
}