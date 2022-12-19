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

   private final UserRepository userRepository;
   private final UserRoleFacade userRoleFacade;
   private final PasswordEncoder passwordEncoder;
   private final WalletFacade walletFacade;
   private final CategoryFacade categoryFacade;

   UserDto addUserWithDefaultsResources(UserDto userDto) {
      User user = UserMapper.toEntity(userDto);
      encodePassword(user);
      addDefaultRoles(user);
      userRepository.save(user);
      addDefaultCategories(user);
      WalletDto wallet = walletFacade.saveDefaultWallet(user);
      return UserMapper.toDto(userRepository.save(user.toBuilder().favoriteWalletId(wallet.getId()).build()));
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
      return UserMapper.toDto(userRepository.findByEmail(principal.getName()).orElseThrow(() -> new UserException(UserError.NOT_FOUND)));
   }

   UserDto setFavoriteWallet(Principal principal, Long walletId) {
      User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new UserException(UserError.NOT_FOUND));
      return UserMapper.toDto(userRepository.save(user.toBuilder().favoriteWalletId(walletId).build()));
   }


}
