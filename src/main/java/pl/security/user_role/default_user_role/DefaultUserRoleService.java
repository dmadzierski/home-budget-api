package pl.security.user_role.default_user_role;

import org.springframework.stereotype.Service;
import pl.security.user_role.UserRole;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultUserRoleService {

  private List<UserRole> defaultUserRole;

  List<UserRole> getDefaultUserRoleFromEnum () {
    return Arrays.stream(DefaultUserRoleType.values()).map(DefaultUserRoleType::getRole).collect(Collectors.toList());
  }

  void setDefaultRoles (List<UserRole> defaultUserRoles) {
    this.defaultUserRole = defaultUserRoles;
  }

  public List<UserRole> getDefaultUserRole () {
    return defaultUserRole;
  }

}
