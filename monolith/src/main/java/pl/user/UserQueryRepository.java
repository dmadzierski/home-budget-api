package pl.user;


import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserQueryRepository extends org.springframework.data.repository.Repository<User, Long> {

   Optional<User> findByEmail(String username);

}
