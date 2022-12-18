package pl.user;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.security.user_role.UserRole;
import pl.wallet.Wallet;
import pl.wallet.category.Category;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "\"user\"")
@ToString
public class User {

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
   private Set<UserRole> roles;

   @OneToMany(cascade = CascadeType.MERGE, mappedBy = "user")
   private Set<Wallet> wallets;

   @ManyToMany
   private Set<Category> categories;

   private Long favoriteWalletId;

   public void addCategory(Category category) {
      if (this.categories == null)
         categories = new HashSet<>();
      categories.add(category);
   }


   void addRole(UserRole userRole) {
      if (roles == null)
         roles = new HashSet<>();
      roles.add(userRole);
   }

   public Set<UserRole> getRoles() {
      return Set.copyOf(roles);
   }

   void addWallet(Wallet wallet) {
      if (wallets == null)
         wallets = new HashSet<>();
      wallets.add(wallet);
   }

   public void removeCategory(Long categoryId) {
      this.setCategories(this.categories.stream().filter(c -> !c.getId().equals(categoryId)).collect(Collectors.toSet()));
   }

   public Set<Category> getCategories() {
      return categories;
   }

   public void setCategories(Set<Category> categories) {
      this.categories = categories;
   }

   Long getId() {
      return id;
   }

   void setId(Long id) {
      this.id = id;
   }

   String getEmail() {
      return email;
   }

   void setEmail(String email) {
      this.email = email;
   }

   String getPassword() {
      return password;
   }

   void setPassword(String password) {
      this.password = password;
   }

   Long getFavoriteWalletId() {
      return favoriteWalletId;
   }

   void setFavoriteWalletId(Long favoriteWalletId) {
      this.favoriteWalletId = favoriteWalletId;
   }

}
