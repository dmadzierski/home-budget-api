package pl.wallet.transaction;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.wallet.Wallet;
import pl.wallet.category.Category;

import javax.persistence.*;
import java.math.BigDecimal;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaction {

  @Column(name = "transaction_id")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  private String description;

  @OneToOne
  private Category category;

  @Column(nullable = false)
  private BigDecimal price;

  @ManyToOne
  private Wallet wallet;

}

