package pl.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity handleAllExceptions (Exception exception, WebRequest request) {
    return ResponseEntity.status(INTERNAL_SERVER_ERROR)
      .body(new ErrorsResponse(Map.of("Server Error", Collections.singletonList(exception.getLocalizedMessage()))).getErrors());
  }

  @Override
  public ResponseEntity handleMethodArgumentNotValid (MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return ResponseEntity
      .status(BAD_REQUEST)
      .body(new ErrorsResponse(exception.getBindingResult().getFieldErrors().stream()
        .collect(Collectors.groupingBy(FieldError::getField))
        .entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey, fieldError -> fieldError.getValue()
          .stream()
          .map(ObjectError::getDefaultMessage)
          .collect(Collectors.toList())))));
  }
}

