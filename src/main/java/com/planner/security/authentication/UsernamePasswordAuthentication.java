package com.planner.security.authentication;

import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class UsernamePasswordAuthentication implements Authentication {

  private boolean authenticate = false;
  private final String username;
  private String password;
  private Collection<? extends GrantedAuthority> authorities = null;

  public UsernamePasswordAuthentication(String username) {
    this.username = username;
    this.authenticate = true;
  }

  public UsernamePasswordAuthentication(String username, String password) {
    this.username = username;
    this.password = password;
    this.authenticate = true;
  }

  public UsernamePasswordAuthentication(String username, String password,
      Collection<? extends GrantedAuthority> authorityList) {
    this.username = username;
    this.password = password;
    this.authorities = authorityList;
    this.authenticate = true;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public Object getCredentials() {
    return password;
  }

  @Override
  public Object getDetails() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return null;
  }

  @Override
  public boolean isAuthenticated() {
    return this.authenticate;
  }

  @Override
  public void setAuthenticated(boolean b) throws IllegalArgumentException {

  }

  @Override
  public String getName() {
    return username;
  }
}
