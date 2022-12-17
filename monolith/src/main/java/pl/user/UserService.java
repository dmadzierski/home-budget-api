package pl.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.exception.EntityNotFoundException;

import java.security.Principal;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UserService {

   private UserRepository userRepository;

   public User getUserByEmail(String email) {
      return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(email, email.getClass()));
   }

   public User getUser(Principal principal) {
      return getUserByEmail(principal.getName());
   }

   boolean emailIsExist(String email) {
      return userRepository.findByEmail(email).isPresent();
   }

   public User saveUser(User user) {
      return userRepository.save(user);
   }

   User setFavoriteWallet(User user, Long walletId) {
      user.setFavoriteWalletId(walletId);
      return saveUser(user);
   }
}
