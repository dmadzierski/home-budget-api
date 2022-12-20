package pl.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import pl.exception.AppError;

@AllArgsConstructor
@Getter
enum UserError implements AppError {
   NOT_FOUND("User not found",HttpStatus.NOT_FOUND);
   private final String message;
   private final HttpStatus status;
}
