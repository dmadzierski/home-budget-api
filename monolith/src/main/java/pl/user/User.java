package pl.user;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.user.user_role.SimpleUserRoleQueryDto;
import pl.wallet.SimpleWalletQueryDto;
import pl.wallet.category.SimpleCategoryQueryDto;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Entity
@Table(name = "\"user\"")
@ToString
class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "user_id")
   private Long id;

   @Email
   @Column(nullable = false, unique = true)
   private String email;


   @Column(nullable = false)
   private String password;

   @Column(nullable = false)
   @ManyToMany(fetch = FetchType.EAGER)
   private Set<SimpleUserRoleQueryDto> roles;

   @OneToMany(cascade = CascadeType.MERGE, mappedBy = "user")
   private Set<SimpleWalletQueryDto> wallets;

   @ManyToMany
   private Set<SimpleCategoryQueryDto> categories;

   private Long favoriteWalletId;

   @Builder(toBuilder = true)
   private User(Long id, String email, String password, Set<SimpleUserRoleQueryDto> roles, Set<SimpleWalletQueryDto> wallets, Set<SimpleCategoryQueryDto> categories, Long favoriteWalletId) {
      this.id = id;
      this.email = email;
      this.password = password;
      this.roles = roles;
      this.wallets = wallets;
      this.categories = categories;
      this.favoriteWalletId = favoriteWalletId;
   }

   public void addCategory(SimpleCategoryQueryDto category) {
      if (this.categories == null)
         categories = new HashSet<>();
      categories.add(category);
   }


   public void addRole(SimpleUserRoleQueryDto userRole) {
      if (roles == null)
         roles = new HashSet<>();
      roles.add(userRole);
   }

   public Set<SimpleUserRoleQueryDto> getRoles() {
      return Collections.unmodifiableSet(roles);
   }


   public void removeCategory(Long categoryId) {
      this.setCategories(categories.stream().filter(c -> !c.getId().equals(categoryId)).collect(Collectors.toSet()));
   }

   public void setCategories(Set<SimpleCategoryQueryDto> categories) {
      this.categories = categories;
   }


   String getEmail() {
      return email;
   }


   String getPassword() {
      return password;
   }


   Long getFavoriteWalletId() {
      return favoriteWalletId;
   }

   public Long getId() {
      return id;
   }
}
