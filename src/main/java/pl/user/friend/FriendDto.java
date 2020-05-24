package pl.user.friend;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@ToString
public class FriendDto {

  @NotNull
  private String name;

  @NotNull
  private LocalDateTime dateOfMakingFriend;

  @Builder
  public FriendDto (String name, LocalDateTime dateOfMakingFriend) {
    this.name = name;
    this.dateOfMakingFriend = dateOfMakingFriend;
  }
}
