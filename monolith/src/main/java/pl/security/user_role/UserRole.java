package pl.security.user_role;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Setter(value = AccessLevel.PACKAGE)
@Table(name = "user_role")
public class UserRole {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_user_role")
   private Long id;

   @Column(unique = true)
   private String roleName;

   private String description;

   private Boolean isDefault;

   public UserRole(String roleName, String description) {
      this.roleName = roleName;
      this.description = description;
   }

}

