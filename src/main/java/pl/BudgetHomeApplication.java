package pl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.security.Principal;

@CrossOrigin("http://localhost:4200")
@RestController
@SpringBootApplication
@EnableSwagger2
public class BudgetHomeApplication {

  public static void main (String[] args) {
    SpringApplication.run(BudgetHomeApplication.class, args);
  }

  @RequestMapping("/user")
  public Principal user (Principal user) {
    return user;
  }
}
