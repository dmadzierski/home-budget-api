package pl.wallet.transaction;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.user.*;
import pl.wallet.category.CategoryDto;
import pl.wallet.category.CategoryError;
import pl.wallet.category.CategoryException;
import pl.wallet.category.CategoryQueryRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.security.Principal;

@Component
@AllArgsConstructor
@SupportedValidationTarget(ValidationTarget.PARAMETERS)
class BackTransactionOrSimpleTransactionValidator implements ConstraintValidator<BackTransactionOrSimpleTransaction, Object[]> {
   private CategoryQueryRepository categoryQueryRepository;
   private UserQueryRepository userQueryRepository;
   private UserFacade userFacade;

   public void initialize(BackTransactionOrSimpleTransaction backTransactionOrSimpleTransaction) {
      backTransactionOrSimpleTransaction.message();
   }

   public boolean isValid(Object[] objs, ConstraintValidatorContext context) {
      UserDto user = userQueryRepository.findByEmail(((Principal) objs[0]).getName()).orElseThrow(() -> new UserException(UserError.NOT_FOUND));
      CategoryDto category = categoryQueryRepository.findByIdAndUserEmail((Long) objs[1], ((Principal) objs[0]).getName()).orElseThrow(() -> new CategoryException(CategoryError.NOT_FOUND));
      return (category.getTransactionType().ordinal() == 4 || category.getTransactionType().ordinal() == 5) == (((TransactionDto) objs[3]).getTransactionIdReference() != null);
   }
}
