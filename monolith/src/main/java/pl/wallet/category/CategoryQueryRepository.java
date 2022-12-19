package pl.wallet.category;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface CategoryQueryRepository extends org.springframework.data.repository.Repository<Category, Long> {

   @Query("SELECT c FROM Category c JOIN c.users u WHERE u.email = :email")
   Set<CategoryDto> findByUser_Email(@Param("email") String emailr);

   @Query("SELECT c FROM Category c INNER JOIN c.users u WHERE :categoryId = c.id AND u.email = :email")
   Optional<CategoryDto> findByIdAndUserEmail(@Param("categoryId") Long categoryId, @Param("email") String email);

}
