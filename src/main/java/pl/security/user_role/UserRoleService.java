package pl.security.user_role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserRoleService {

  private UserRoleRepository userRoleRepository;

  @Autowired
  public UserRoleService (UserRoleRepository userRoleRepository) {
    this.userRoleRepository = userRoleRepository;
  }

  public UserRole findRole (String roleName) {
    return userRoleRepository.findByRoleName(roleName).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  public UserRole save (UserRole userRole) {
    return userRoleRepository.save(userRole);
  }


}
