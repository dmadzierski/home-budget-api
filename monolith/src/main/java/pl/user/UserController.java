package pl.user;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import pl.user.user_role.UserRoleFacade;
import pl.wallet.WalletDto;
import pl.wallet.WalletFacade;
import pl.wallet.category.CategoryFacade;

import java.security.Principal;

@Controller
@AllArgsConstructor
class UserController {

   private final UserService userService;
   private final UserRoleFacade userRoleFacade;
   private final PasswordEncoder passwordEncoder;
   private final WalletFacade walletFacade;
   private final CategoryFacade categoryFacade;

   UserDto addUserWithDefaultsResources(UserDto userDto) {
      User user = UserMapper.toEntity(userDto);
      encodePassword(user);
      addDefaultRoles(user);
      userService.saveUser(user);
      addDefaultCategories(user);
      WalletDto wallet = walletFacade.saveDefaultWallet(user);
      return UserMapper.toDto(userService.setFavoriteWallet(user, wallet.getId()));
   }

   private void addDefaultCategories(User user) {
      categoryFacade.addDefaultCategoriesToUser(user.getEmail());
   }

   private void addDefaultRoles(User user) {
      userRoleFacade.addDefaultRolesToUser(user);
   }


   private User encodePassword(User user) {
      return user.toBuilder().password(passwordEncoder.encode(user.getPassword())).build();
   }

   UserDto getUserByPrincipal(Principal principal) {
      return UserMapper.toDto(userService.getUser(principal));
   }

   UserDto setFavoriteWallet(Principal principal, Long walletId) {
      User user = userService.getUser(principal);
      return UserMapper.toDto(userService.setFavoriteWallet(user, walletId));
   }


}
