package pl.user;

import pl.user.friend.Friend;
import pl.user.friend.FriendMapper;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

  private UserMapper () {
  }

  public static UserDto toDto (User user) {
    return UserDto.builder()
      .email(user.getEmail())
      .favoriteWalletId(user.getFavoriteWalletId())
      .build();
  }


  public static User toEntity (UserDto userDto) {
    User user = new User();
    user.setPassword(userDto.getPassword());
    user.setEmail(userDto.getEmail());
    return user;
  }

  public static UserDto toDtoWithFriend (User user, List<Friend> friends) {
    return UserDto.builder()
      .email(user.getEmail())
      .friends(friends.stream().map(FriendMapper::toDto).collect(Collectors.toList()))
      .build();
  }
}
