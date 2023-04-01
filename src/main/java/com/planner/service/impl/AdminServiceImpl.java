package com.planner.service.impl;

import com.planner.entities.UserDetails;
import com.planner.entities.Users;
import com.planner.exception.user.UserDataNotFoundException;
import com.planner.exception.EntityMisMatchException;
import com.planner.factory.UserMapStructFactory;
import com.planner.model.UserAndDetailsVO;
import com.planner.model.UserCredentials;
import com.planner.model.UserDetailsVO;
import com.planner.model.UserVO;
import com.planner.repository.UserRepository;
import com.planner.service.AdminService;
import com.planner.utils.status.UserType;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserMapStructFactory userMapStructFactory;

  @Override
  public UserVO createAdmin(UserCredentials userCredentials) {
    Users userAdmin = userMapStructFactory.getUserForPersisting(userCredentials);
    return userMapStructFactory.createVoForRestResponse(userRepository.save(userAdmin));
  }

  @Override
  public boolean saveAllAdmin(List<UserCredentials> adminList) {
    if (adminList.isEmpty()) {
      throw new UserDataNotFoundException("Not Found");
    }
    adminList.stream().forEach(admin -> {
      userRepository.save(userMapStructFactory.getUserForPersisting(admin));
    });
    return true;
  }

  @Override
  public List<UserVO> getAllAdmins() {
    List<Users> adminList = userRepository.findUserByUserType(UserType.ADMINISTRATOR);
    return userMapStructFactory.createVoForRestResponse(adminList);
  }

  @Override
  public UserVO getAdminById(String id) {
    Users persistedUser = userRepository.getById(Long.valueOf(id));
    return userMapStructFactory.createVoForRestResponse(persistedUser);
  }

  @Override
  public boolean saveAdminDetails(UserDetailsVO userDetailsVO, String adminId) {
    Users admin = userRepository.getById(Long.valueOf(adminId));
    if (userDetailsVO.getUserName() == null || userDetailsVO.getUserName().isEmpty()) {
      userDetailsVO.setUserName(admin.getUsername());
    }
    if (admin.getUserDetails() != null || admin.getUserType().getValue()
        .equals(UserType.MEMBER.getValue())) {
      return false;
    }
    UserDetails userDetails = userMapStructFactory.getUserDetailsForPersisting(userDetailsVO);
    admin.setUserDetails(userDetails);
    userRepository.save(admin);
    return true;
  }

  @Override
  public UserAndDetailsVO getAdminWithDetails(String userId) {
    Optional<Users> admin = userRepository.findById(Long.valueOf(userId));
    UserAndDetailsVO userAndDetailsVO = new UserAndDetailsVO();
    if(admin.isPresent()){
      if(admin.get().getUserType().getValue().equals(UserType.ADMINISTRATOR.getValue())) {
        userAndDetailsVO = userMapStructFactory.createUserWithDetailsVO(admin.get());
      }
      else {
        throw new EntityMisMatchException("Please Try again");
      }
    }
    return userAndDetailsVO;
  }

}
