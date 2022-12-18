package pl.user;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import pl.security.user_role.UserRoleProvider;
import pl.wallet.Wallet;
import pl.wallet.WalletProvider;
import pl.wallet.category.CategoryProvider;

import java.security.Principal;

@Controller
@AllArgsConstructor
class UserController {

   private final UserService userService;
   private final UserRoleProvider userRoleProvider;
   private final PasswordEncoder passwordEncoder;
   private final WalletProvider walletProvider;
   private final CategoryProvider categoryProvider;

   UserDto addUserWithDefaultsResources(UserDto userDto) {
      User user = UserMapper.toEntity(userDto);
      encodePassword(user);
      addDefaultRoles(user);
      User savedUser = userService.saveUser(user);
      addDefaultCategories(user);
      Wallet wallet = walletProvider.saveDefaultWallet(user);
      return UserMapper.toDto(userService.setFavoriteWallet(savedUser, wallet.getId()));
   }

   private void addDefaultCategories(User user) {
      categoryProvider.getDefaultCategories().forEach(user::addCategory);
   }

   private void addDefaultRoles(User user) {
      userRoleProvider.findDefaultRoles().forEach(user::addRole);
   }


   private void encodePassword(User user) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
   }

   UserDto getUserByPrincipal(Principal principal) {
      return UserMapper.toDto(userService.getUser(principal));
   }

   UserDto setFavoriteWallet(Principal principal, Long walletId) {
      User user = userService.getUser(principal);
      return UserMapper.toDto(userService.setFavoriteWallet(user, walletId));
   }


}
