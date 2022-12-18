package pl.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@AllArgsConstructor
class UserUniqueValidator implements ConstraintValidator<UniqueUserEmail, String> {

   private final UserService userService;

   @Override
   public void initialize(UniqueUserEmail uniqueUserEmail) {
      uniqueUserEmail.message();
   }

   @Override
   public boolean isValid(String email, ConstraintValidatorContext context) {
      return !userService.emailIsExist(email);
   }
}