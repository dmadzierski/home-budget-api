package pl.user.friend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.exception.CanNotNotAddOldFriendException;
import pl.user.User;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FriendService {

  private FriendRepository friendRepository;

  @Autowired
  public FriendService (FriendRepository friendRepository) {
    this.friendRepository = friendRepository;
  }

  public boolean areTheyAlreadyFriends (User user, User friend) {
    if(friendRepository.areTheyAlreadyFriends(user, friend) == 0)
      return false;
    else
      throw new CanNotNotAddOldFriendException("User already have this user as friend");
  }


  public Friend saveFriend (User user, User friend) {
    Friend friend1 = Friend.builder()
      .dateOfMakingFiends(LocalDateTime.now())
      .user(user)
      .friend(friend)
      .build();
    Friend friend2 = Friend.builder()
      .dateOfMakingFiends(friend1.getDateOfMakingFiends())
      .user(friend)
      .friend(user)
      .build();
    friendRepository.save(friend2);
    return friendRepository.save(friend1);
  }

  public List<Friend> getFriendByUser (User user) {
    return friendRepository.getFriendsByUser(user);
  }
}
