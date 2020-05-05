package pl.security.user_role;


import lombok.Builder;
import lombok.Getter;
import pl.user.UserDto;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
public class UserRoleDto {

  private Long id;

  @NotNull(message = "User role should habe name")
  private String name;

  private String description;

  private Set<UserDto> users;

  @Builder
  public UserRoleDto (Long id, String name, String description, Set<UserDto> users) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.users = users;
  }
}
