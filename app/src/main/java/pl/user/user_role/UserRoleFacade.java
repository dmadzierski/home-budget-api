package pl.user.user_role;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.user.SimpleUserQueryDto;

@AllArgsConstructor
@Service
public class UserRoleFacade {

   private final UserRoleRepository userRoleRepository;

   public void addDefaultRolesToUser(SimpleUserQueryDto user) {
      userRoleRepository.getDefaultRoles().forEach(us -> user.addRole(UserRoleMapper.toQueryDto(us)));
   }
}
