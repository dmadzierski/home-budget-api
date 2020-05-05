package pl.test_tool;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import pl.test_tool.web.RequestTool;
import pl.user.UserDto;

@Deprecated(forRemoval = true)
public class UserTool {
  public static String randomCorrectEmail () {
    return RandomTool.getRandomString(10) + "@o2.pl";
  }

  public static String randomCorrectPassword () {
    return RandomTool.getRandomString(10);
  }

  public static UserDto createRandomCorrectUserDto () {
    return UserDto.builder()
      .email(randomCorrectEmail())
      .password(randomCorrectPassword())
      .build();
  }

  public static UserDto register (MockMvc mockMvc) {
    UserDto userDto = UserTool.createRandomCorrectUserDto();
    UserDto userDtoExpectedResult = UserDto.builder().email(userDto.getEmail()).build();
    RequestTool.checkResponse(HttpMethod.POST, mockMvc, "/register", userDto, HttpStatus.CREATED, userDtoExpectedResult);
    return userDto;
  }


}
