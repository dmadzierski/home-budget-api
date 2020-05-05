package pl.user.friend;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class FriendDto {

  private String name;

  private LocalDateTime dateOfMakingFriend;

  @Builder
  public FriendDto (String name, LocalDateTime dateOfMakingFriend) {
    this.name = name;
    this.dateOfMakingFriend = dateOfMakingFriend;
  }
}
