package pl.user.user_role;


public class UserRoleMapper {

   public static SimpleUserRoleQueryDto toQueryDto(UserRole userRole) {
      return SimpleUserRoleQueryDto.builder()
         .id(userRole.getId())
         .build();
   }

   public static UserRole toEntity(SimpleUserRoleQueryDto simpleUserRoleQueryDto) {
      return UserRole.builder()
         .description(simpleUserRoleQueryDto.getDescription())
         .roleName(simpleUserRoleQueryDto.getRoleName())
         .build();
   }
}
