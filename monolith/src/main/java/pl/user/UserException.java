package pl.user;

import pl.exception.AppError;
import pl.exception.AppRuntimeException;


 class UserException extends AppRuntimeException {
    UserException(AppError appError) {
      super(appError);
   }
}
