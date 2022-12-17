package pl.wallet.category;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.user.User;
import pl.wallet.transaction.TransactionType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter(value = AccessLevel.PACKAGE)
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
      if (users == null) {
         this.users = new ArrayList<>();
         users.add(user);
      } else
         users.add(user);
   }


   public List<User> getUser() {
      return this.users;
   }

}
