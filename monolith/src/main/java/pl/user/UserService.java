package pl.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UserService {

   private final UserRepository userRepository;
   private final UserQueryRepository userQueryRepository;

   public User saveUser(User user) {
      return userRepository.save(user);
   }

   boolean emailIsExist(String email) {
      return userRepository.findByEmail(email).isPresent();
   }

   User getUser(Principal principal) {
      return getUserByEmail(principal.getName());
   }

   User getUserByEmail(String email) {
      return userRepository.findByEmail(email).orElseThrow(() -> new UserException(UserError.NOT_FOUND));
   }

   User setFavoriteWallet(User user, Long walletId) {
      return saveUser(user.toBuilder().favoriteWalletId(walletId).build());
   }
}
