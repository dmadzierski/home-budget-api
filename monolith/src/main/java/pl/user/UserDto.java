package pl.user;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import pl.security.user_role.UserRoleDto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Set;

@Getter
@ToString
@EqualsAndHashCode
public class UserDto {

  @Null(message = "New user can not have id")
  private Long id;

  @UniqueUserEmail(message = "Email is used by another account")
  @Email(message = "Must be a well-formed email address")
  @NotNull(message = "User must have email")
  private String email;

  private Set<UserRoleDto> roles;

  @Length(min = 5, message = "Password length must be longer than 5")
  @NotNull(message = "User must have password")
  private String password;

  private Long favoriteWalletId;

  @Builder
  public UserDto (@Null(message = "New user can not have id") Long id, @Email(message = "Must be a well-formed email address") @NotNull(message = "User must have email") String email, Set<UserRoleDto> roles, @Length(min = 5, message = "Password length must be longer than 5") @NotNull(message = "User must have password") String password, Long favoriteWalletId) {
    this.id = id;
    this.email = email;
    this.roles = roles;
    this.password = password;
    this.favoriteWalletId = favoriteWalletId;
  }
}
