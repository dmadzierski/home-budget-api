package pl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.exception.EntityNotFoundException;

import java.security.Principal;

@Service
public class UserService {

  private UserRepository userRepository;

  @Autowired
  public UserService (UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User getUserByEmail (String email) {
    return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(email, email.getClass()));
  }

  public User getUser (Principal principal) {
    return getUserByEmail(principal.getName());
  }

  boolean emailIsExist (String email) {
    return userRepository.findByEmail(email).isPresent();
  }

  public User saveUser (User user) {
    return userRepository.save(user);
  }

  public User setFavoriteWallet (User user, Long walletId) {
    user.setFavoriteWalletId(walletId);
    return saveUser(user);
  }
}
