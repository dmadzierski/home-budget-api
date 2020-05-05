package pl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import pl.security.user_role.UserRole;
import pl.security.user_role.UserRoleService;

import static pl.security.user_role.default_user_role.DefaultUserRoleType.USER_ROLE;

@Controller
public class UserController {

  private UserService userService;
  private UserRoleService userRoleService;
  private PasswordEncoder passwordEncoder;

  @Autowired
  public UserController (UserService userService, UserRoleService userRoleService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.userRoleService = userRoleService;
    this.passwordEncoder = passwordEncoder;
  }

  UserDto addWithDefaultRoleAndDefaultCategory (UserDto userDto) {
    User user = UserMapper.toEntity(userDto);
    UserRole userRole = userRoleService.findRole(USER_ROLE.getName());
    user.addRole(userRole);
    encodePassword(user);
    user = userService.saveUser(user);
    return UserMapper.toDto(user);
  }

  private void encodePassword (User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
  }
}
