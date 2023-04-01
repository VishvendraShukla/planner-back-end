package com.planner.security.provider;

import com.planner.security.UserDetailsSecurity;
import com.planner.security.authentication.UsernamePasswordAuthentication;
import com.planner.security.model.UserDetailsSecurityImpl;
import com.planner.utils.hash.PasswordHasher;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service("usernameAuthProvider")
public class UsernameAuthProvider implements AuthenticationProvider {

  @Autowired
  private UserDetailsSecurity userDetailsSecurity;

  @Resource(name = "passwordHasher")
  private PasswordHasher passwordHasher;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    if (username == null) {
      throw new BadCredentialsException("Bad Request");
    }
    UserDetailsSecurityImpl user = (UserDetailsSecurityImpl) userDetailsSecurity.loadUserByUsername(
        username);
    if (authentication.getCredentials() != null) {
      String password = (String) authentication.getCredentials();
      if (passwordHasher.verifyHash(password, user.getPassword())) {
        return new UsernamePasswordAuthentication(user.getUsername(), user.getPassword(),
            user.getAuthorities());
      }
    }
    if (authentication.getCredentials() == null) {
      return new UsernamePasswordAuthentication(user.getUsername(), user.getPassword(),
          user.getAuthorities());
    }
    throw new BadCredentialsException("Bad Request");
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return UsernamePasswordAuthentication.class.equals(aClass);
  }

}
