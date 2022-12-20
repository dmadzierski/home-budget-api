package pl.wallet.transaction;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.user.UserDto;
import pl.user.UserQueryService;
import pl.wallet.category.CategoryDto;
import pl.wallet.category.CategoryQueryService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.security.Principal;

@Component
@AllArgsConstructor
@SupportedValidationTarget(ValidationTarget.PARAMETERS)
class BackTransactionOrSimpleTransactionValidator implements ConstraintValidator<BackTransactionOrSimpleTransaction, Object[]> {
   private CategoryQueryService categoryQueryService;
   private UserQueryService userQueryService;

   public void initialize(BackTransactionOrSimpleTransaction backTransactionOrSimpleTransaction) {
      backTransactionOrSimpleTransaction.message();
   }

   public boolean isValid(Object[] objs, ConstraintValidatorContext context) {
      UserDto user = userQueryService.findByEmail(((Principal) objs[0]).getName());
      CategoryDto category = categoryQueryService.findByIdAndUserEmail((Long) objs[1], ((Principal) objs[0]).getName());
      return (category.getTransactionType().ordinal() == 4 || category.getTransactionType().ordinal() == 5) == (((TransactionDto) objs[3]).getTransactionIdReference() != null);
   }
}
