package pl.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {
   @Bean
   UserFacade userFacade(UserRepository userRepository) {
      return new UserFacade(userRepository);
   }
}
