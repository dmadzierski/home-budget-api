package pl.user;


import lombok.*;
import org.hibernate.validator.constraints.Length;
import pl.user.user_role.UserRoleDto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Set;

@Getter(AccessLevel.PACKAGE)
@ToString
@EqualsAndHashCode
public class UserDto {

   @Null(message = "New user can not have id")
   private final Long id;

   @UniqueUserEmail(message = "Email is used by another account")
   @Email(message = "Must be a well-formed email address")
   @NotNull(message = "User must have email")
   private final String email;

   private final Set<UserRoleDto> roles;

   @Length(min = 5, message = "Password length must be longer than 5")
   @NotNull(message = "User must have password")
   private final String password;

   private final Long favoriteWalletId;

   @Builder(toBuilder = true)
   UserDto(@Null(message = "New user can not have id") Long id, @Email(message = "Must be a well-formed email address") @NotNull(message = "User must have email") String email, Set<UserRoleDto> roles, @Length(min = 5, message = "Password length must be longer than 5") @NotNull(message = "User must have password") String password, Long favoriteWalletId) {
      this.id = id;
      this.email = email;
      this.roles = roles;
      this.password = password;
      this.favoriteWalletId = favoriteWalletId;
   }
}
