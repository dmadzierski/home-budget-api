package pl.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@AllArgsConstructor
public class UserProvider {
   private final UserRepository userRepository;

   public User getUserByEmail(String email) {
      return userRepository.findByEmail(email).orElseThrow();
   }

   public User getUser(Principal principal) {
      return getUserByEmail(principal.getName());
   }

   public User saveUser(User user) {
      return userRepository.save(user);
   }
}
