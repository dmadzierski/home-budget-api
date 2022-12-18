package pl.user;


import org.springframework.stereotype.Repository;

@Repository
public interface UserQueryRepository extends org.springframework.data.repository.Repository<User, Long> {


}
