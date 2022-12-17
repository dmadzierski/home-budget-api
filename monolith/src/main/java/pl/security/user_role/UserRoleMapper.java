package pl.security.user_role;

class UserRoleMapper {


   public static UserRoleDto toDto(UserRole userRole) {
      return UserRoleDto.builder()
         .description(userRole.getDescription())
         .name(userRole.getRoleName())
         .build();
   }
}
