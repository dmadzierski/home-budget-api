package pl.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
  @Value("${cors.allowed-origins}")
  private String allowedOrigins;

  @Bean
  public PasswordEncoder passwordEncoder () {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Override
  protected void configure (HttpSecurity http) throws Exception {
    http
      .httpBasic()
      .and()
      .authorizeRequests()
      .antMatchers("/index.html", "/register", "/login", "/", "/user").permitAll()
      .anyRequest().authenticated()
      .and()
      .formLogin().usernameParameter("email")
      .and()
      .cors()
      .and()
      .csrf().disable()
      .headers().frameOptions().disable();
  }

  @Bean
  public WebMvcConfigurer corsConfigurer () {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings (CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins(allowedOrigins);
      }
    };
  }
}
