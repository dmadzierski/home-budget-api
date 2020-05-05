package pl.user.friend;

public class FriendMapper {
  public static FriendDto toDto (Friend friend) {
    return FriendDto.builder()
      .name(friend.getFriend().getEmail())
      .dateOfMakingFriend(friend.getDateOfMakingFiends())
      .build();
  }
}
