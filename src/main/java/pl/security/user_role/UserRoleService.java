package pl.security.user_role;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor

public class UserRoleService {

  private UserRoleRepository userRoleRepository;


  public UserRole findRole (String roleName) {
    return userRoleRepository.findByRoleName(roleName).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  public UserRole save (UserRole userRole) {
    return userRoleRepository.save(userRole);
  }


}
