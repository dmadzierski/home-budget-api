package pl.wallet.category;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

interface CategoryRepository {
   @Query("SELECT c FROM Category c WHERE c.isDefault = true")
   Set<Category> getDefaultCategories();

   @Query("SELECT c FROM Category c INNER JOIN c.users u WHERE :categoryId = c.id AND u.email = :email")
   Optional<Category> findByIdAndUserEmail(@Param("categoryId") Long categoryId, @Param("email") String email);
}
