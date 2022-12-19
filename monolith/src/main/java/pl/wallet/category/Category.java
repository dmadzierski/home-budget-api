package pl.wallet.category;

import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.user.User;
import pl.wallet.transaction.TransactionType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category {

   private Boolean isDefault = false;

   @Column(name = "category_id")
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false)
   private String name;

   private String description;

   @Column(nullable = false)
   @Enumerated
   private TransactionType transactionType;

   @ManyToMany(mappedBy = "categories")
   private Set<User> users;


   @Builder(toBuilder = true)
   private Category(Boolean isDefault, Long id, String name, String description, TransactionType transactionType, Set<User> users) {
      this.isDefault = isDefault;
      this.id = id;
      this.name = name;
      this.description = description;
      this.transactionType = transactionType;
      this.users = users;
   }

   public void addUser(User user) {
      if (users == null)
         this.users = new HashSet<>();
      users.add(user);
   }

   public TransactionType getTransactionType() {
      return transactionType;
   }


   public Long getId() {
      return id;
   }


   String getName() {
      return name;
   }


   String getDescription() {
      return description;
   }

}
