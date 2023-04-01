package com.planner.service.impl;

import com.planner.entities.RoleEntity;
import com.planner.entities.UserDetails;
import com.planner.entities.Users;
import com.planner.exception.user.IncorrectDataException;
import com.planner.exception.user.UserBlockedException;
import com.planner.exception.user.UserDataNotFoundException;
import com.planner.exception.user.UserNotFoundException;
import com.planner.factory.UserMapStructFactory;
import com.planner.model.LoginCredentials;
import com.planner.model.SignupVO;
import com.planner.model.UserAndDetailsVO;
import com.planner.model.UserCredentials;
import com.planner.model.UserDetailsVO;
import com.planner.model.UserVO;
import com.planner.repository.RoleRepository;
import com.planner.repository.UserRepository;
import com.planner.service.UserService;
import com.planner.utils.status.UserType;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private UserMapStructFactory userMapStructFactory;


  @Override
  public List<UserVO> getAllUsers() {
    return userMapStructFactory.createVoForRestResponse(
        userRepository.findUserByUserType(UserType.MEMBER));
  }

  @Override
  public UserVO getUserById(String id) {
    Users persistedUser = userRepository.getById(Long.valueOf(id));
    return userMapStructFactory.createVoForRestResponse(persistedUser);
  }

  @Override
  public UserVO save(UserCredentials user) {
    Users adornUser = userMapStructFactory.getUserForPersisting(user);
    Optional<RoleEntity> userRole = roleRepository.findByName("ROLE_USER");
    if (userRole.isPresent() && userRole.get().getName().equalsIgnoreCase("ROLE_USER")) {
      adornUser.addRole(userRole.get());
    }
    return userMapStructFactory.createVoForRestResponse(
        userRepository.save(adornUser));
  }

  @Override
  public boolean saveAll(List<UserCredentials> usersList) {
    if (usersList.isEmpty()) {
      throw new UserDataNotFoundException("List Empty");
    }
    usersList.forEach(user -> {
      userRepository.save(
          userMapStructFactory.getUserForPersisting(user));
    });
    return true;
  }

  @Override
  public UserVO loginUpdate(LoginCredentials loginCredentials) {
    Optional<Users> user = userRepository.findUserByUsername(loginCredentials.getUsername());
    Users savedUser = user.orElseThrow(UserDataNotFoundException::new);
    if (savedUser.isBlocked()) {
      throw new UserBlockedException("Unable to provide access");
    }
    savedUser.setLastLogin(Calendar.getInstance());
    Users updatedUser = userRepository.save(savedUser);
    return userMapStructFactory.createVoForRestResponse(updatedUser);
  }

  @Override
  public boolean saveUserDetails(UserDetailsVO userDetailsVO, String userId) {
    Users user = userRepository.findById(Long.valueOf(userId))
        .orElseThrow(UserNotFoundException::new);
    if (userDetailsVO.getUserName() == null || userDetailsVO.getUserName().isEmpty()) {
      userDetailsVO.setUserName(user.getUsername());
    }
    if (user.getUserDetails() != null || user.getUserType().getValue()
        .equals(UserType.ADMINISTRATOR.getValue())) {
      throw new IncorrectDataException();
    }
    UserDetails userDetails = userMapStructFactory.getUserDetailsForPersisting(userDetailsVO);
    user.setUserDetails(userDetails);
    userRepository.save(user);
    return true;
  }

  @Override
  public UserAndDetailsVO getUserWithDetails(String userId) {
    Users user = userRepository.findById(Long.valueOf(userId))
        .orElseThrow(UserDataNotFoundException::new);
    return userMapStructFactory.createUserWithDetailsVO(user);
  }

  @Override
  public Users signUp(SignupVO signupVO) {
    return userRepository.save(userMapStructFactory.mapToEntity(signupVO));
  }

}
