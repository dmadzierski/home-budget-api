package pl.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.security.user_role.default_user_role.DefaultUserRoleType;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


  @Bean
  public PasswordEncoder passwordEncoder () {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Override
  protected void configure (HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
      .antMatchers("/").permitAll()
      .antMatchers("/register").permitAll()
      .antMatchers("/user_role").hasRole(DefaultUserRoleType.ADMIN_ROLE.getName())
      .anyRequest().authenticated()
      .and()
      .formLogin().permitAll()
      .and()
      .csrf().disable()
      .headers().frameOptions().disable();
  }
}
