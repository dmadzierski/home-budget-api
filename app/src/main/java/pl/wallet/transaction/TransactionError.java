package pl.wallet.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import pl.exception.AppError;

@Getter
@AllArgsConstructor
enum TransactionError implements AppError {
   NOT_FOUND("Transaction not found", HttpStatus.NOT_FOUND),
   CAN_NOT_HAVE_ID("Transaction can not have id", HttpStatus.BAD_REQUEST),
   CAN_NOT_SET_FINISHED("Transaction can not set finished", HttpStatus.BAD_REQUEST);

   private final String message;
   private final HttpStatus status;
}
