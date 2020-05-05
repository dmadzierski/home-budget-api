package pl.security.user_role.default_user_role;

import java.util.Collections;
import java.util.List;

public interface DefaultUserRole {
  List<DefaultUserRoleType> DEFAULT_USER_ROLES_TYPES = Collections.singletonList(DefaultUserRoleType.USER_ROLE);
}
