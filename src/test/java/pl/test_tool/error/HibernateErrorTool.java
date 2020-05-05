package pl.test_tool.error;

import pl.exception.ErrorsResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HibernateErrorTool {

  public static ErrorsResponse buildErrorResponse (HibernateConstraintError... hibernateConstraintErrors) {
    Map<String, List<String>> errorsMap = Arrays.stream(hibernateConstraintErrors).collect(Collectors.groupingBy(HibernateConstraintError::getFieldName))
      .entrySet().stream()
      .collect(Collectors.toMap(Map.Entry::getKey, fieldError -> fieldError.getValue()
        .stream()
        .map(HibernateConstraintError::getMessage)
        .collect(Collectors.toList())));
    return new ErrorsResponse(errorsMap);
  }
}
