package pl.wallet.category;

import pl.exception.AppError;
import pl.exception.AppRuntimeException;

class CategoryException extends AppRuntimeException {
   CategoryException(AppError appError) {
      super(appError);
   }
}
