package com.planner.service;

import com.planner.entities.Users;
import com.planner.model.LoginCredentials;
import com.planner.model.SignupVO;
import com.planner.model.UserAndDetailsVO;
import com.planner.model.UserCredentials;
import com.planner.model.UserDetailsVO;
import com.planner.model.UserVO;
import java.util.List;

public interface UserService {

  List<UserVO> getAllUsers();

  UserVO getUserById(String id);

  UserVO save(UserCredentials user);

  boolean saveAll(List<UserCredentials> usersList);

  UserVO loginUpdate(LoginCredentials loginCredentials);

  boolean saveUserDetails(UserDetailsVO userDetailsVO,String userId);

  UserAndDetailsVO getUserWithDetails(String userId);

  Users signUp(SignupVO signupVO);


}
