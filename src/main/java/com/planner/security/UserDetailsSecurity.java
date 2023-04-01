package com.planner.security;

import com.planner.entities.Users;
import com.planner.repository.UserRepository;
import com.planner.security.model.UserDetailsSecurityImpl;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsSecurity implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    Optional<Users> user = userRepository.findUserByUsername(username);
    if(!user.isPresent()){
      throw new UsernameNotFoundException("Not Found");
    }
    Users savedUser = user.get();
    return UserDetailsSecurityImpl.build(savedUser);
  }
}
