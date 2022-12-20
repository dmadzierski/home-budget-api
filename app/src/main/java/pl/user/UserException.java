package pl.user;

import pl.exception.AppRuntimeException;

 class UserException extends AppRuntimeException {

    UserException(UserError userError) {
      super(userError);
   }
}
