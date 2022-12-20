package pl.user.user_role;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name = "user_role")
class UserRole {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_user_role")
   private Long id;

   @Column(unique = true)
   private String roleName;

   private String description;

   private Boolean isDefault;

   @Builder(toBuilder = true)
   private UserRole(String roleName, String description) {
      this.roleName = roleName;
      this.description = description;
   }


   public Long getId() {
      return id;
   }
}

