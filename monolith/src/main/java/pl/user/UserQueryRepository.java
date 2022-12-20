package pl.user;


import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UserQueryRepository extends org.springframework.data.repository.Repository<User, Long> {

   Optional<UserDto> findByEmail(String username);
}
