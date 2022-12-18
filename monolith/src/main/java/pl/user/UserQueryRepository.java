package pl.user;


import org.springframework.stereotype.Repository;

@Repository
interface UserQueryRepository extends org.springframework.data.repository.Repository<User, Long> {


}
