package pl.test_tool.web;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RequestToolWithAuth {

  public static MvcResult checkResponse (HttpMethod httpMethod, MockMvc mockMvc, String uri, Object object, HttpStatus expectedStatus, Object expectedContent, String userName) {
    String jsonStringObject = JsonTool.asJsonString(object);
    String jsonStringExpectedContent = JsonTool.asJsonString(expectedContent);
    return RequestToolWithAuth.checkResponse(httpMethod, mockMvc, uri, jsonStringObject, expectedStatus, jsonStringExpectedContent, userName);
  }

  public static MvcResult checkResponseDelete (MockMvc mockMvc, String uri, HttpStatus expectedStatus, String userName) {
    try {
      return mockMvc.perform(delete(uri).with(user(userName))
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().is(expectedStatus.value()))
        .andReturn();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static MvcResult checkResponseDelete (MockMvc mockMvc, String uri, HttpStatus expectedStatus, Object expectedObject, String userName) {
    try {
      String jsonStringExpectedObject = JsonTool.asJsonString(expectedObject);
      return mockMvc.perform(delete(uri).with(user(userName))
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().is(expectedStatus.value()))
        .andExpect(content().json(jsonStringExpectedObject))
        .andReturn();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  public static MvcResult checkResponse (HttpMethod httpMethod, MockMvc mockMvc, String uri, String jsonStringObject, HttpStatus expectedStatus, String jsonStringExpectedContent, String userName) {
    try {
      return buildResultActions(mockMvc, uri, jsonStringObject, httpMethod, userName)
        .andExpect(status().is(expectedStatus.value()))
        .andExpect(content().json(jsonStringExpectedContent))
        .andReturn();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static MvcResult checkResponseGet (MockMvc mockMvc, String uri) {
    try {
      return mockMvc.perform(get(uri)
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  private static ResultActions buildResultActions (MockMvc mockMvc, String uri, String jsonStringObject, HttpMethod httpMethod, String userName) throws Exception {
    switch (httpMethod) {
      case HEAD:
        return mockMvc.perform(head(uri).with(user(userName))
          .content(jsonStringObject)
          .accept(MediaType.APPLICATION_JSON_VALUE)
          .contentType(MediaType.APPLICATION_JSON_VALUE));
      case POST:
        return mockMvc.perform(post(uri).with(user(userName))
          .content(jsonStringObject)
          .accept(MediaType.APPLICATION_JSON_VALUE)
          .contentType(MediaType.APPLICATION_JSON_VALUE));
      case PUT:
        return mockMvc.perform(put(uri).with(user(userName))
          .content(jsonStringObject)
          .accept(MediaType.APPLICATION_JSON_VALUE)
          .contentType(MediaType.APPLICATION_JSON_VALUE));
      case PATCH:
        return mockMvc.perform(patch(uri).with(user(userName))
          .content(jsonStringObject)
          .accept(MediaType.APPLICATION_JSON_VALUE)
          .contentType(MediaType.APPLICATION_JSON_VALUE));
      case DELETE:
        return mockMvc.perform(delete(uri).with(user(userName))
          .content(jsonStringObject)
          .accept(MediaType.APPLICATION_JSON_VALUE)
          .contentType(MediaType.APPLICATION_JSON_VALUE));

      case OPTIONS:
        return mockMvc.perform(options(uri).with(user(userName))
          .content(jsonStringObject)
          .accept(MediaType.APPLICATION_JSON_VALUE)
          .contentType(MediaType.APPLICATION_JSON_VALUE));
      default:
        throw new Exception("Incorrect http method");
    }
  }

}
