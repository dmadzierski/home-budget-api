package pl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UserUniqueValidator implements ConstraintValidator<Unique, String> {

  private UserService userService;

  @Autowired
  UserUniqueValidator (ApplicationContext applicationContext) {
    userService = applicationContext.getBean(UserService.class);
  }

  @Override
  public void initialize (Unique unique) {
    unique.message();
  }

  @Override
  public boolean isValid (String email, ConstraintValidatorContext context) {
    return !userService.emailIsExist(email);
  }
}
