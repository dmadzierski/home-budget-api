package pl.wallet.transaction;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.user.User;
import pl.user.UserProvider;
import pl.wallet.category.Category;
import pl.wallet.category.CategoryProvider;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.security.Principal;

@Component
@AllArgsConstructor
@SupportedValidationTarget(ValidationTarget.PARAMETERS)
class BackTransactionOrSimpleTransactionValidator implements ConstraintValidator<BackTransactionOrSimpleTransaction, Object[]> {
   private CategoryProvider categoryProvider;
   private UserProvider userProvider;

   public void initialize(BackTransactionOrSimpleTransaction backTransactionOrSimpleTransaction) {
      backTransactionOrSimpleTransaction.message();
   }

   public boolean isValid(Object[] objs, ConstraintValidatorContext context) {
      User user = userProvider.getUser((Principal) objs[0]);
      Category category = categoryProvider.getCategory(user, (Long) objs[1]);
      return (category.getTransactionType().ordinal() == 4 || category.getTransactionType().ordinal() == 5) == (((TransactionDto) objs[3]).getTransactionIdReference() != null);
   }
}
