package pl.user;

import org.springframework.data.repository.Repository;

interface SqlUserQueryRepository extends UserQueryRepository, Repository<UserDto, Long> {
}
