package pl.wallet.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import pl.exception.AppError;

@AllArgsConstructor
@Getter
public enum CategoryError implements AppError {
   NOT_FOUND("Category not found", HttpStatus.NOT_FOUND);;
   private final String message;
   private final HttpStatus status;
}
