package pl.wallet;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.user.SimpleUserQueryDto;

import javax.persistence.*;

@Entity
@Table(name = "wallet")
@Getter
@NoArgsConstructor
public class SimpleWalletQueryDto {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne
   private SimpleUserQueryDto user;

   @Builder(toBuilder = true)
   public SimpleWalletQueryDto(Long id, SimpleUserQueryDto user) {
      this.id = id;
      this.user = user;
   }
}
