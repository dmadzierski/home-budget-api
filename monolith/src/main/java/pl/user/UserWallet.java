package pl.user;

import org.springframework.data.jpa.domain.Specification;
import pl.wallet.transaction.SimpleTransactionQueryDto;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

class UserWallet implements Specification<SimpleTransactionQueryDto> {
   UserWallet(User user) {
   }

   @Override
   public Predicate toPredicate(Root<SimpleTransactionQueryDto> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
      return null;
   }
}
