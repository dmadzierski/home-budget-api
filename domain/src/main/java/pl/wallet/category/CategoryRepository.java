package pl.wallet.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
interface CategoryRepository extends JpaRepository<Category, Long> {
   @Query("SELECT c FROM Category c WHERE c.isDefault = true")
   Set<Category> getDefaultCategories();

   @Query("SELECT c FROM Category c INNER JOIN c.users u WHERE :categoryId = c.id AND u.email = :email")
   Optional<Category> findByIdAndUserEmail(@Param("categoryId") Long categoryId, @Param("email") String email);
}
