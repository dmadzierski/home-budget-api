package pl.security.user_role.default_user_role;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import pl.security.user_role.UserRoleService;

import java.util.stream.Collectors;

@Controller
@AllArgsConstructor

public class DefaultUserRoleController {


  private DefaultUserRoleService defaultUserRoleService;
  private UserRoleService userRoleService;


  @EventListener(ApplicationReadyEvent.class)
  public void saveRolesAndSetDefaultRoles () {
    try {
      defaultUserRoleService.setDefaultRoles(defaultUserRoleService
        .getDefaultUserRoleFromEnum()
        .stream().map(userRoleService::save)
        .filter(userRole -> DefaultUserRole.DEFAULT_USER_ROLES_TYPES
          .stream().anyMatch(defaultUserRoleType -> defaultUserRoleType.getRole().equals(userRole)))
        .collect(Collectors.toList()));
    } catch (Exception ignored) {
    }
  }
}
