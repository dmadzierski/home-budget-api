package pl.wallet.transaction;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = BackTransactionOrSimpleTransactionValidator.class)
@Target(ElementType.METHOD)
@Retention(RUNTIME)
@Documented
public @interface BackTransactionOrSimpleTransaction {
  String message ();

  Class<?>[] groups () default {};

  Class<? extends Payload>[] payload () default {};

  String message2 ();
}
