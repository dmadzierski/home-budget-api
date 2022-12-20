package pl.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserQueryService {
   private final UserQueryRepository userQueryRepository;


   public UserDto findByEmail(String email) {
      return userQueryRepository.findByEmail(email).orElseThrow(() -> new UserException(UserError.NOT_FOUND));
   }
}
