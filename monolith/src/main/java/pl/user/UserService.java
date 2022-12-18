package pl.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UserService {

   private final UserRepository userRepository;

   public User saveUser(User user) {
      return userRepository.save(user);
   }

   boolean emailIsExist(String email) {
      return userRepository.findByEmail(email).isPresent();
   }

   User getUser(Principal principal) {
      return getUserByEmail(principal.getName());
   }

   private User getUserByEmail(String email) {
      return userRepository.findByEmail(email).orElseThrow();
   }

   User setFavoriteWallet(User user, Long walletId) {
      user.setFavoriteWalletId(walletId);
      return saveUser(user);
   }
}
