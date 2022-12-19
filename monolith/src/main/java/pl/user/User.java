package pl.user;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.user.user_role.UserRole;
import pl.wallet.Wallet;
import pl.wallet.category.Category;

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

   @Builder(toBuilder = true)
   public User(Long id, String email, String password, Set<UserRole> roles, Set<Wallet> wallets, Set<Category> categories, Long favoriteWalletId) {
      this.id = id;
      this.email = email;
      this.password = password;
      this.roles = roles;
      this.wallets = wallets;
      this.categories = categories;
      this.favoriteWalletId = favoriteWalletId;
   }

   public void addCategory(Category category) {
      if (this.categories == null)
         categories = new HashSet<>();
      categories.add(category);
   }


   public void addRole(UserRole userRole) {
      if (roles == null)
         roles = new HashSet<>();
      roles.add(userRole);
   }

   public Set<UserRole> getRoles() {
      return Collections.unmodifiableSet(roles);
   }

   void addWallet(Wallet wallet) {
      if (wallets == null)
         wallets = new HashSet<>();
      wallets.add(wallet);
   }

   public void removeCategory(Long categoryId) {
      this.setCategories(this.categories.stream().filter(c -> !c.getId().equals(categoryId)).collect(Collectors.toSet()));
   }

   public Set<Wallet> getWallets() {
      return Collections.unmodifiableSet(wallets);
   }

   public Set<Category> getCategories() {
      return Collections.unmodifiableSet(categories);
   }

   public void setCategories(Set<Category> categories) {
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


}
