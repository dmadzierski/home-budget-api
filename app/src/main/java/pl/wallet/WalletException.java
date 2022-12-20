package pl.wallet;

import pl.exception.AppError;
import pl.exception.AppRuntimeException;

 class WalletException extends AppRuntimeException {
    public WalletException(AppError appError) {
       super(appError);
    }
 }
