package pl.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.security.user_role.UserRole;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
class CustomUserDetailsService implements UserDetailsService {

   private final UserProvider userProvider;


   @Override
   public UserDetails loadUserByUsername(String username) {
      User user;
      try {
         user = userProvider.getUserByEmail(username);
      } catch (UserException e) {
         throw new UsernameNotFoundException("User not found");
      }
      return new org.springframework.security.core.userdetails.User(
         user.getEmail(),
         user.getPassword(),
         convertAuthorities(user.getRoles()));
   }

   private Set<GrantedAuthority> convertAuthorities(Collection<UserRole> userRoles) {
      return userRoles.stream().map(k -> new SimpleGrantedAuthority(k.getRoleName())).collect(Collectors.toSet());
   }
}