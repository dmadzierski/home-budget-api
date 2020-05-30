package pl.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.exception.ErrorsResponse;
import pl.test_tool.RandomTool;
import pl.test_tool.UserTool;
import pl.test_tool.error.HibernateErrorTool;
import pl.test_tool.web.RequestTool;
import pl.user.UserDto;

import static pl.test_tool.error.UserError.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class RegisterResourceTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void should_create_new_user () {
    //given
    UserDto userDto = UserTool.createRandomCorrectUserDto();
    UserDto userDtoExpectedResult = UserDto.builder().email(userDto.getEmail()).favoriteWalletId(1L).build();
    //when

    //then
    register(userDto, HttpStatus.CREATED, userDtoExpectedResult);
  }

  @Test
  void new_user_should_not_have_id () {
    //given
    UserDto userDto = UserDto.builder().email(UserTool.randomCorrectEmail()).password(UserTool.randomCorrectPassword()).id(20L).build();
    ErrorsResponse response = HibernateErrorTool.buildErrorResponse(ID_NOT_NULL);
    //when

    //then
    register(userDto, HttpStatus.BAD_REQUEST, response);
  }

  @Test
  void new_user_should_have_email () {
    //given
    UserDto userDto = UserDto.builder().password(UserTool.randomCorrectPassword()).build();
    ErrorsResponse response = HibernateErrorTool.buildErrorResponse(EMAIL_NOT_NULL);
    //when

    //then
    register(userDto, HttpStatus.BAD_REQUEST, response);
  }

  @Test
  void new_user_should_have_password () {
    //given
    UserDto userDto = UserDto.builder().email(UserTool.randomCorrectEmail()).build();
    ErrorsResponse response = HibernateErrorTool.buildErrorResponse(PASSWORD_NOT_NULL);
    //when

    //then
    register(userDto, HttpStatus.BAD_REQUEST, response);
  }

  @Test
  void new_user_should_have_password_longer_than_5 () {
    //given
    UserDto userDto = UserDto.builder().email(UserTool.randomCorrectEmail()).password(RandomTool.getRandomString(4)).build();
    ErrorsResponse response = HibernateErrorTool.buildErrorResponse(PASSWORD_MIN_LENGTH);
    //when

    //then
    register(userDto, HttpStatus.BAD_REQUEST, response);
  }

  @Test
  void new_user_should_have_well_preformed_email () {
    //given
    UserDto userDto = UserDto.builder().email(RandomTool.getRandomString()).password(UserTool.randomCorrectPassword()).build();
    ErrorsResponse response = HibernateErrorTool.buildErrorResponse(EMAIL_WELL_FORMED);
    //when

    //then
    register(userDto, HttpStatus.BAD_REQUEST, response);
  }

  @Test
  void new_user_should_have_unique_email () {
    //given
    UserDto userDto = UserTool.createRandomCorrectUserDto();
    UserDto userDtoExpectedResult = UserDto.builder().email(userDto.getEmail()).favoriteWalletId(1L).build();
    ErrorsResponse response = HibernateErrorTool.buildErrorResponse(EMAIL_UNIQUE);
    //when
    register(userDto, HttpStatus.CREATED, userDtoExpectedResult);
    //then
    register(userDto, HttpStatus.BAD_REQUEST, response);
  }

  private MvcResult register (Object object, HttpStatus expectHttpStatus, Object resultObject) {
    return RequestTool.checkResponse(HttpMethod.POST, this.mockMvc, "/register", object, expectHttpStatus, resultObject);
  }

}
