package pl.security.user_role;

public class UserRoleMapper {
  private UserRoleMapper () {
  }

  public static UserRoleDto toDto (UserRole userRole) {
    return UserRoleDto.builder()
      .description(userRole.getDescription())
      .name(userRole.getRoleName())
      .build();
  }
}
