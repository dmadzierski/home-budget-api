package pl.security.user_role;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UserRoleService {

   private final UserRoleRepository userRoleRepository;

   public List<UserRole> findDefaultRoles() {
      return userRoleRepository.getDefaultRoles();
   }
}
