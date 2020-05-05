package pl.security.user_role.default_user_role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import pl.security.user_role.UserRoleService;

import java.util.stream.Collectors;

@Controller
public class DefaultUserRoleController {

  private DefaultUserRoleService defaultUserRoleService;
  private UserRoleService userRoleService;

  @Autowired
  public DefaultUserRoleController (DefaultUserRoleService defaultUserRoleService, UserRoleService userRoleService) {
    this.defaultUserRoleService = defaultUserRoleService;
    this.userRoleService = userRoleService;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void saveRolesAndSetDefaultRoles () {
    defaultUserRoleService.setDefaultRoles(defaultUserRoleService
      .getDefaultUserRoleFromEnum()
      .stream().map(userRoleService::save)
      .filter(userRole -> DefaultUserRole.DEFAULT_USER_ROLES_TYPES
        .stream().anyMatch(defaultUserRoleType -> defaultUserRoleType.getRole().equals(userRole)))
      .collect(Collectors.toList()));
  }
}
