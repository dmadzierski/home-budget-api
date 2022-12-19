package pl.wallet.category;

import pl.exception.AppError;
import pl.exception.AppRuntimeException;

public class CategoryException extends AppRuntimeException {
   public CategoryException(AppError appError) {
      super(appError);
   }
}
