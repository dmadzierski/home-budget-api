package pl.user;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import pl.security.user_role.UserRole;
import pl.security.user_role.UserRoleService;
import pl.wallet.Wallet;
import pl.wallet.WalletService;
import pl.wallet.category.default_category.DefaultCategoryService;

import java.security.Principal;

import static pl.security.user_role.default_user_role.DefaultUserRoleType.USER_ROLE;

@Controller
@AllArgsConstructor
public class UserController {

  private UserService userService;
  private UserRoleService userRoleService;
  private PasswordEncoder passwordEncoder;
  private DefaultCategoryService defaultCategoryService;
  private WalletService walletService;

  UserDto addUserWithDefaultsResources (UserDto userDto) {
    User user = UserMapper.toEntity(userDto);
    UserRole userRole = userRoleService.findRole(USER_ROLE.getName());
    user.addRole(userRole);
    encodePassword(user);
    User savedUser = userService.saveUser(user);
    defaultCategoryService.addDefaultCategories(user);
    Wallet wallet = walletService.saveDefaultWallet(user);
    savedUser.setFavoriteWalletId(wallet.getId());
    savedUser = userService.saveUser(user);
    return UserMapper.toDto(savedUser);
  }


  private void encodePassword (User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
  }

  UserDto getUserByPrincipal (Principal principal) {
    return UserMapper.toDto(userService.getUserByPrincipal(principal));
  }
}
