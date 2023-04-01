package com.planner.security.provider;

import com.planner.security.UserDetailsSecurity;
import com.planner.security.authentication.JWTTokenAuthentication;
import com.planner.security.model.JWTTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service("jwtAuthTokenProvider")
public class JWTAuthTokenProvider implements AuthenticationProvider {

  @Autowired
  private JWTTokenHelper jwtTokenHelper;

  @Autowired
  private UserDetailsSecurity userDetailsSecurity;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String token = "";
    if (authentication instanceof JWTTokenAuthentication) {
      token = ((JWTTokenAuthentication) authentication).getToken();
    }
    if (!token.isEmpty() && jwtTokenHelper.validateJwtToken(token)) {
      String username = jwtTokenHelper.getUserNameFromJwtToken(token);
      UserDetails user = userDetailsSecurity.loadUserByUsername(username);
      return new JWTTokenAuthentication(user);
    }
    throw new BadCredentialsException("Bad Credentials");
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return JWTTokenAuthentication.class.equals(aClass);
  }

}
