package pl.wallet.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.user.User;

import java.util.Optional;
import java.util.Set;

@Repository
interface CategoryRepository extends JpaRepository<Category, Long> {

}
