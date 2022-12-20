package pl.user.user_role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface UserRoleRepository extends JpaRepository<UserRole, Long> {

   @Query("SELECT r FROM UserRole r WHERE r.isDefault = true")
   List<UserRole> getDefaultRoles();
}
