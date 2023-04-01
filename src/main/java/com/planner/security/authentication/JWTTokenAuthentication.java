package com.planner.security.authentication;

import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JWTTokenAuthentication implements Authentication {

  private static final long serialVersionUID = -5987734285015710394L;
  private String token;

  private UserDetails userDetails;

  public JWTTokenAuthentication(UserDetails userDetails) {
    this.userDetails = userDetails;
  }

  public JWTTokenAuthentication(String token) {
    this.token = token;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return userDetails.getAuthorities();
  }

  @Override
  public Object getCredentials() {
    return userDetails.getPassword();
  }

  @Override
  public Object getDetails() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return userDetails;
  }

  @Override
  public boolean isAuthenticated() {
    return true;
  }

  @Override
  public void setAuthenticated(boolean b) throws IllegalArgumentException {

  }

  @Override
  public String getName() {
    return userDetails.getUsername();
  }

  public String getToken() {
    return token;
  }

}
