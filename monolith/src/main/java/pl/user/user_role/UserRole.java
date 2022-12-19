package pl.user.user_role;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
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

   @Builder(toBuilder = true)
   public UserRole(String roleName, String description) {
      this.roleName = roleName;
      this.description = description;
   }

   Long getId() {
      return id;
   }

   void setId(Long id) {
      this.id = id;
   }

   public String getRoleName() {
      return roleName;
   }


   String getDescription() {
      return description;
   }


   Boolean getDefault() {
      return isDefault;
   }

}

