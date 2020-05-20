package pl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@SpringBootApplication
@EnableSwagger2
public class BudgetHomeApplication {

  public static void main (String[] args) {
    SpringApplication.run(BudgetHomeApplication.class, args);
  }

}
