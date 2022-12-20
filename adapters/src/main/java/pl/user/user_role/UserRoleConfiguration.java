package pl.user.user_role;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
 class UserRoleConfiguration {
   @Bean
   UserRoleFacade userRoleFacade(UserRoleRepository userRoleRepository) {
      return new UserRoleFacade(userRoleRepository);
   }
}
