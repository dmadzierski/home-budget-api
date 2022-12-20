package pl.user.user_role;

import org.springframework.data.jpa.repository.JpaRepository;

interface SqlUserRoleRepository extends UserRoleRepository, JpaRepository<UserRole, Long> {
}
