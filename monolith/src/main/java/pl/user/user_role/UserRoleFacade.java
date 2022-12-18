package pl.user.user_role;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.user.User;

@AllArgsConstructor
@Service
public class UserRoleFacade {

   private final UserRoleRepository userRoleRepository;

   public void addDefaultRolesToUser(User user) {
      userRoleRepository.getDefaultRoles().forEach(user::addRole);
   }
}
