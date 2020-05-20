package pl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UserUniqueValidator implements ConstraintValidator<UniqueUserEmail, String> {

  private UserService userService;

  @Autowired
  UserUniqueValidator (ApplicationContext applicationContext) {
    userService = applicationContext.getBean(UserService.class);
  }

  @Override
  public void initialize (UniqueUserEmail uniqueUserEmail) {
    uniqueUserEmail.message();
  }

  @Override
  public boolean isValid (String email, ConstraintValidatorContext context) {
    return !userService.emailIsExist(email);
  }
}
