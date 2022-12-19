package pl.user;

import org.springframework.data.jpa.domain.Specification;
import pl.wallet.transaction.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

class UserWallet implements Specification<Transaction> {
   UserWallet(User user) {
   }

   @Override
   public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
      return null;
   }
}
