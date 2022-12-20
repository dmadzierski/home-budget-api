package pl.user.user_role;


import lombok.Builder;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode
@Builder
public class UserRoleDto {

   private Long id;

   @NotNull(message = "User role should habe name")
   private String name;

   private String description;

   @Builder(toBuilder = true)
   UserRoleDto(Long id, String name, String description) {
      this.id = id;
      this.name = name;
      this.description = description;
   }

}
