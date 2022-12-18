package pl.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import pl.exception.AppError;

@Getter
@AllArgsConstructor
 class UserError implements AppError {
   ;

   private final String message;
   private final HttpStatus status;
}
