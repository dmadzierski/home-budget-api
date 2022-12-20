package pl.wallet.transaction;


import pl.exception.AppError;
import pl.exception.AppRuntimeException;

class TransactionException extends AppRuntimeException {
   public TransactionException(AppError appError) {
      super(appError);
   }
}
