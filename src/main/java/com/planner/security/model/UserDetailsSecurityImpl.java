package com.planner.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.planner.entities.Users;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsSecurityImpl implements UserDetails {

  private final Long id;
  private final String username;
  @JsonIgnore
  private final String password;
  private final Collection<? extends GrantedAuthority> authorities;

  public UserDetailsSecurityImpl(Long id, String username, String password,String salt,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.authorities = authorities;
  }

  public static UserDetailsSecurityImpl build(Users user) {
    List<GrantedAuthority> authorities = user.getRoleEntities().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());
    return new UserDetailsSecurityImpl(
        user.getId(),
        user.getUsername(),
        user.getPassword(),
        user.getSalt(),
        authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public Long getId() {
    return id;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserDetailsSecurityImpl user = (UserDetailsSecurityImpl) o;
    return Objects.equals(id, user.id);
  }

}

