package pl.user.user_role;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.user.User;

@AllArgsConstructor
@Service
public class UserRoleFacade {

   private final UserRoleQueryRepository userRoleQueryRepository;

   public void addDefaultRolesToUser(User user) {
      userRoleQueryRepository.getDefaultRoles().forEach(user::addRole);
   }
}
