package pl.wallet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WalletConfiguration {
   @Bean
   WalletFacade walletFacade(WalletRepository walletRepository) {
      return new WalletFacade(walletRepository);
   }
}
