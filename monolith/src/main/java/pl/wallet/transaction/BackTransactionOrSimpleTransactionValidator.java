package pl.wallet.transaction;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.user.User;
import pl.user.UserService;
import pl.wallet.category.Category;
import pl.wallet.category.CategoryService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.security.Principal;

@Component
@AllArgsConstructor
@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class BackTransactionOrSimpleTransactionValidator implements ConstraintValidator<BackTransactionOrSimpleTransaction, Object[]> {
  private CategoryService categoryService;
  private UserService userService;

  public void initialize (BackTransactionOrSimpleTransaction backTransactionOrSimpleTransaction) {
    backTransactionOrSimpleTransaction.message();
  }

  public boolean isValid (Object[] objs, ConstraintValidatorContext context) {
    User user = userService.getUser((Principal) objs[0]);
    Category category = categoryService.getCategory(user, (Long) objs[1]);
    return (category.getTransactionType().ordinal() == 4 || category.getTransactionType().ordinal() == 5) == (((TransactionDto) objs[3]).getTransactionIdReference() != null);
  }
}
