package pl.security.user_role;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserRoleFacade {

   private final UserRoleRepository userRoleRepository;

   public List<UserRole> findDefaultRoles() {
      return userRoleRepository.getDefaultRoles();
   }

}
