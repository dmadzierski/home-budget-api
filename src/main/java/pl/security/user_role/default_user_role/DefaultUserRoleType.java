package pl.security.user_role.default_user_role;

import pl.security.user_role.UserRole;

public enum DefaultUserRoleType {
  USER_ROLE("USER_ROLE", " User role"),
  ADMIN_ROLE("ADMIN_ROLE", "Admin role");

  private String name;
  private String description;

  DefaultUserRoleType (String name, String description) {
    this.name = name;
    this.description = description;
  }

  public UserRole getRole () {
    return new UserRole(name, description);
  }


  public String getName () {
    return name;
  }
}
