package pl.user;


import java.util.Optional;

interface UserQueryRepository {

   Optional<UserDto> findByEmail(String username);
}
