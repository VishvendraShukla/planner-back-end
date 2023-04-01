package com.planner.factory;

import com.planner.entities.UserDetails;
import com.planner.entities.Users;
import com.planner.model.SignupVO;
import com.planner.model.UserAndDetailsVO;
import com.planner.model.UserCredentials;
import com.planner.model.UserDetailsVO;
import com.planner.model.UserVO;
import java.util.List;

public interface UserMapStructFactory {

  Users getUserForPersisting(UserCredentials userCredentials);

  UserVO createVoForRestResponse(Users User);

  List<UserVO> createVoForRestResponse(List<Users> User);

  UserDetails getUserDetailsForPersisting(UserDetailsVO userDetailsVO);

  UserAndDetailsVO createUserWithDetailsVO(Users user);

  Users mapToEntity(SignupVO signupVO);

}
