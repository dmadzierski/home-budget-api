package pl.wallet.transaction;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TransactionConfiguration {
   @Bean
   TransactionFacade transactionFacade(TransactionRepository transactionRepository){
      return new TransactionFacade(transactionRepository);
   }
}
