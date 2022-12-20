package pl.user;

import java.util.Optional;

interface UserRepository {
   Optional<User> findByEmail(String username);

   User save(User user);
}
