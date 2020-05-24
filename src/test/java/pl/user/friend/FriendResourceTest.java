package pl.user.friend;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import pl.user.UserDto;
import pl.user.UserResource;
import pl.user.UserTool;

import java.security.Principal;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FriendResourceTest {

  private UserResource userResource;
  private FriendResource friendResource;

  @Autowired
  FriendResourceTest (UserResource userResource, FriendResource friendResource) {
    this.userResource = userResource;
    this.friendResource = friendResource;
  }

  @Test
  void should_add_friend () {
    //given
    UserDto userDto = UserTool.registerRandomUser(userResource);
    UserDto friend = UserTool.registerRandomUser(userResource);
    Principal principal = userDto::getEmail;
    //when
    ResponseEntity<UserDto> userDtoResponseEntity = friendResource.addFriend(principal, friend);
    //then
    Assertions.assertThat(userDtoResponseEntity).isNotNull();
    Assertions.assertThat(userDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    Assertions.assertThat(userDtoResponseEntity.getBody().getFriends()).matches(k -> k.stream().allMatch(c -> c.getDateOfMakingFriend() != null && c.getName().equals(friend.getEmail())));
    Assertions.assertThat(userDtoResponseEntity.getBody()).isEqualToIgnoringNullFields(userDto);
  }


}
