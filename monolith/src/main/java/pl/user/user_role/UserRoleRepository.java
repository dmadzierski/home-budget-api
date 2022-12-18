package pl.user.user_role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface UserRoleRepository extends JpaRepository<UserRole, Long> {


}
