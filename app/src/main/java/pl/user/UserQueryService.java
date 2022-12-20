package pl.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.user.UserError;
import pl.user.UserException;


@Service
@AllArgsConstructor
public class UserQueryService {
   private final UserQueryRepository userQueryRepository;


   public UserDto findByEmail(String email) {
      return userQueryRepository.findByEmail(email).orElseThrow(() -> new UserException(UserError.NOT_FOUND));
   }
}
