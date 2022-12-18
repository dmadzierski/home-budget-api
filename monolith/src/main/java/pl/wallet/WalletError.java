package pl.wallet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import pl.exception.AppError;

@Getter
@AllArgsConstructor
 enum WalletError implements AppError {
   NOT_FOUND("Wallet not found", HttpStatus.NOT_FOUND),
   CAN_NOT_HAVE_ID("New wallet can not have id", HttpStatus.BAD_REQUEST)
   ;
   private final String message;
   private final HttpStatus status;
}
