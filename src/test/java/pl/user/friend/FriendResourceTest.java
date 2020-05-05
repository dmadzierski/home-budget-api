package pl.user.friend;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import pl.user.RegisterResource;
import pl.user.UserDto;
import pl.user.UserTool;

import java.security.Principal;
import java.util.Arrays;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FriendResourceTest {

  private RegisterResource registerResource;
  private FriendResource friendResource;

  @Autowired
  FriendResourceTest (RegisterResource registerResource, FriendResource friendResource) {
    this.registerResource = registerResource;
    this.friendResource = friendResource;
  }

  @Test
  void should_add_friend () {
    //given
    UserDto userDto = UserTool.registerRandomUser(registerResource);
    UserDto friend = UserTool.registerRandomUser(registerResource);
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
