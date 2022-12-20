package pl.wallet.category;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.user.SimpleUserQueryDto;
import pl.wallet.transaction.TransactionType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
@Getter
@NoArgsConstructor
public class SimpleCategoryQueryDto {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;


   @Enumerated
   private TransactionType transactionType;

   @ManyToMany
   private Set<SimpleUserQueryDto> users;


   @Builder(toBuilder = true)
   public SimpleCategoryQueryDto(Long id, Set<SimpleUserQueryDto> users) {
      this.id = id;
      this.users = users;
   }

   public void addUser(SimpleUserQueryDto toQueryDto) {
      if (users == null)
         users = new HashSet<>();
      users.add(toQueryDto);
   }
}
