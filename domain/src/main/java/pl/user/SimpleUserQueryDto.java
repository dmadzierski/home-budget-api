package pl.user;

import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.user.user_role.SimpleUserRoleQueryDto;
import pl.wallet.category.SimpleCategoryQueryDto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "\"user\"")
@NoArgsConstructor
public class SimpleUserQueryDto {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToMany
   private Set<SimpleCategoryQueryDto> categories;

   private String email;

   @ManyToMany
   private Set<SimpleUserRoleQueryDto> userRoles;

   @Builder(toBuilder = true)
   public SimpleUserQueryDto(Long id, Set<SimpleCategoryQueryDto> categories, String email, Set<SimpleUserRoleQueryDto> userRoles) {
      this.id = id;
      this.categories = categories;
      this.email = email;
      this.userRoles = userRoles;
   }

   public void addRole(SimpleUserRoleQueryDto toQueryDto) {
      if (userRoles == null)
         userRoles = new HashSet<>();
      userRoles.add(toQueryDto);
   }
}
