package pl.security.user_role;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserRoleService {

  private UserRoleRepository userRoleRepository;

  public List<UserRole> findDefaultRoles () {
    return userRoleRepository.getDefaultRoles();
  }
}
