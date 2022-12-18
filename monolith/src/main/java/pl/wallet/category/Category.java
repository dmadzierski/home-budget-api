package pl.wallet.category;

import lombok.NoArgsConstructor;
import pl.user.User;
import pl.wallet.transaction.TransactionType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


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
   private List<User> users;


   public void addUser(User user) {
      if (users == null)
         this.users = new ArrayList<>();
      users.add(user);
   }


   public TransactionType getTransactionType() {
      return transactionType;
   }

   public void setTransactionType(TransactionType transactionType) {
      this.transactionType = transactionType;
   }

   public Boolean getDefault() {
      return isDefault;
   }

   void setDefault(Boolean aDefault) {
      isDefault = aDefault;
   }

   public Long getId() {
      return id;
   }

   void setId(Long id) {
      this.id = id;
   }

   String getName() {
      return name;
   }

   void setName(String name) {
      this.name = name;
   }

   String getDescription() {
      return description;
   }

   void setDescription(String description) {
      this.description = description;
   }

   void setUsers(List<User> users) {
      this.users = List.copyOf(users);
   }
}
