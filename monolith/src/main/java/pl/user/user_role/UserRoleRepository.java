package pl.user.user_role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UserRoleRepository extends JpaRepository<UserRole, Long> {


}
