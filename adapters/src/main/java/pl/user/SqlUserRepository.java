package pl.user;

import org.springframework.data.jpa.repository.JpaRepository;

interface SqlUserRepository extends UserRepository, JpaRepository<User, Long> {
}
