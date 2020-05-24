package pl.security.user_role;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import pl.user.UserDto;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@EqualsAndHashCode
@Builder
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
