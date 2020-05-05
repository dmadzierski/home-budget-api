package pl.test_tool.web;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RequestTool {
  public static MvcResult checkResponse (HttpMethod httpMethod, MockMvc mockMvc, String uri, Object object, HttpStatus expectedStatus, Object expectedContent) {
    String jsonStringObject = JsonTool.asJsonString(object);
    String jsonStringExpectedContent = JsonTool.asJsonString(expectedContent);
    return checkResponse(httpMethod, mockMvc, uri, jsonStringObject, expectedStatus, jsonStringExpectedContent);
  }

  public static MvcResult checkResponse (HttpMethod httpMethod, MockMvc mockMvc, String uri, Object object, HttpStatus expectedStatus, String jsonStringExpectedContent) {
    String jsonStringObject = JsonTool.asJsonString(object);
    return checkResponse(httpMethod, mockMvc, uri, jsonStringObject, expectedStatus, jsonStringExpectedContent);
  }

  public static MvcResult checkResponse (HttpMethod httpMethod, MockMvc mockMvc, String uri, String jsonStringObject, HttpStatus expectedStatus, Object expectedContent) {
    String jsonStringExpectedContent = JsonTool.asJsonString(expectedContent);
    return checkResponse(httpMethod, mockMvc, uri, jsonStringObject, expectedStatus, jsonStringExpectedContent);
  }

  public static MvcResult checkResponseWithAuth (HttpMethod httpMethod, MockMvc mockMvc, String uri, String jsonStringObject, HttpStatus expectedStatus, String jsonStringExpectedContent, String userName) {
    try {
      return buildResultActions(mockMvc, uri, jsonStringObject, httpMethod)
        .andExpect(status().is(expectedStatus.value()))
        .andExpect(content().json(jsonStringExpectedContent))
        .andReturn();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  public static MvcResult checkResponse (HttpMethod httpMethod, MockMvc mockMvc, String uri, String jsonStringObject, HttpStatus expectedStatus, String jsonStringExpectedContent) {
    try {
      return buildResultActions(mockMvc, uri, jsonStringObject, httpMethod)
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


  private static ResultActions buildResultActions (MockMvc mockMvc, String uri, String jsonStringObject, HttpMethod httpMethod) throws Exception {
    switch (httpMethod) {
      case HEAD:
        return mockMvc.perform(head(uri)
          .content(jsonStringObject)
          .accept(MediaType.APPLICATION_JSON_VALUE)
          .contentType(MediaType.APPLICATION_JSON_VALUE));
      case POST:
        return mockMvc.perform(post(uri)
          .content(jsonStringObject)
          .accept(MediaType.APPLICATION_JSON_VALUE)
          .contentType(MediaType.APPLICATION_JSON_VALUE));
      case PUT:
        return mockMvc.perform(put(uri)
          .content(jsonStringObject)
          .accept(MediaType.APPLICATION_JSON_VALUE)
          .contentType(MediaType.APPLICATION_JSON_VALUE));
      case PATCH:
        return mockMvc.perform(patch(uri)
          .content(jsonStringObject)
          .accept(MediaType.APPLICATION_JSON_VALUE)
          .contentType(MediaType.APPLICATION_JSON_VALUE));
      case DELETE:
        return mockMvc.perform(delete(uri)
          .content(jsonStringObject)
          .accept(MediaType.APPLICATION_JSON_VALUE)
          .contentType(MediaType.APPLICATION_JSON_VALUE));

      case OPTIONS:
        return mockMvc.perform(options(uri)
          .content(jsonStringObject)
          .accept(MediaType.APPLICATION_JSON_VALUE)
          .contentType(MediaType.APPLICATION_JSON_VALUE));
      default:
        throw new Exception("Incorrect http method");
    }
  }

}
