package pl.wallet.category;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.user.UserFacade;
import pl.user.UserQueryService;

@Configuration
class CategoryConfiguration {
   @Bean
   CategoryFacade categoryFacade(CategoryRepository categoryRepository, UserQueryService userQueryService, UserFacade userFacade) {
      return new CategoryFacade(categoryRepository,userQueryService, userFacade);
   }
}
