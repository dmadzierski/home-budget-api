package pl.wallet.transaction;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.user.User;
import pl.user.UserFacade;
import pl.wallet.category.CategoryDto;
import pl.wallet.category.CategoryFacade;
import pl.wallet.transaction.dto.TransactionDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.security.Principal;

@Component
@AllArgsConstructor
@SupportedValidationTarget(ValidationTarget.PARAMETERS)
class BackTransactionOrSimpleTransactionValidator implements ConstraintValidator<BackTransactionOrSimpleTransaction, Object[]> {
   private CategoryFacade categoryFacade;
   private UserFacade userFacade;

   public void initialize(BackTransactionOrSimpleTransaction backTransactionOrSimpleTransaction) {
      backTransactionOrSimpleTransaction.message();
   }

   public boolean isValid(Object[] objs, ConstraintValidatorContext context) {
      User user = userFacade.getUser((Principal) objs[0]);
      CategoryDto category = categoryFacade.getCategory(user, (Long) objs[1]);
      return (category.getTransactionType().ordinal() == 4 || category.getTransactionType().ordinal() == 5) == (((TransactionDto) objs[3]).getTransactionIdReference() != null);
   }
}
