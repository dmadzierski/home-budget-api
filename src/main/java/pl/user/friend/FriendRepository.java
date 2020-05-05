package pl.user.friend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.user.User;

import java.util.List;

@Repository
interface FriendRepository extends JpaRepository<Friend, Long> {

  @Query("SELECT COUNT(f) FROM Friend f  WHERE f.user = :user AND f.friend = :friend")
  Long areTheyAlreadyFriends (@Param("user") User user, @Param("friend") User friend);

  List<Friend> getFriendsByUser (User user);
}
