package pl.user.user_role;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
@Getter
@NoArgsConstructor
public class SimpleUserRoleQueryDto {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String roleName;

   private String description;

   @Builder(toBuilder = true)
   public SimpleUserRoleQueryDto(Long id, String roleName, String description) {
      this.id = id;
      this.roleName = roleName;
      this.description = description;
   }

}
