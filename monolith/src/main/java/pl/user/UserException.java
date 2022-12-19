package pl.user;

import pl.exception.AppError;
import pl.exception.AppRuntimeException;


public class UserException extends AppRuntimeException {
   public UserException(AppError appError) {
      super(appError);
   }
}
