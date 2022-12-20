package pl.wallet.category;

import pl.exception.AppError;
import pl.exception.AppRuntimeException;

 class CategoryException extends AppRuntimeException {
    public CategoryException(AppError appError) {
       super(appError);
    }
 }
