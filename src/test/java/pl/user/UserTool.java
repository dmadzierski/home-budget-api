package pl.user;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import pl.test_tool.RandomTool;
import pl.test_tool.web.RequestTool;


public class UserTool {

  private static String[] emailDomainName = new String[]{"o2.pl", "gmail.com", "wp.pl", "onet.pl"};

  private static String randomCorrectEmail () {
    return RandomTool.getRandomString(10) + "@" + randomEmailDomainName();
  }

  private static String randomEmailDomainName () {
    return UserTool.emailDomainName[RandomTool.getNumberInteger() % UserTool.emailDomainName.length];
  }

  private static UserDto createRandomUserDto () {
    return UserDto.builder()
      .email(randomCorrectEmail())
      .password(RandomTool.getRandomString())
      .build();
  }

  public static UserDto register (MockMvc mockMvc, Long walletId) {
    UserDto userDto = UserTool.createRandomUserDto();
    UserDto userDtoExpectedResult = UserDto.builder().email(userDto.getEmail()).favoriteWalletId(walletId).build();
    RequestTool.checkResponse(HttpMethod.POST, mockMvc, "/register", userDto, HttpStatus.CREATED, userDtoExpectedResult);
    return userDto;
  }

  @Deprecated
  public static UserDto registerRandomUser (UserController userController) {
    UserDto userDto = UserTool.createRandomUserDto();
    return userController.addUserWithDefaultsResources(userDto);
  }

  public static UserDto registerRandomUser (UserResource userResource) {
    UserDto userDto = UserTool.createRandomUserDto();
    return userResource.register(userDto).getBody();
  }
}
