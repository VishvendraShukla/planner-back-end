package com.planner.service;

import com.planner.model.UserAndDetailsVO;
import com.planner.model.UserCredentials;
import com.planner.model.UserDetailsVO;
import com.planner.model.UserVO;
import java.util.List;

public interface AdminService {

  UserVO createAdmin(UserCredentials userCredentials);

  boolean saveAllAdmin(List<UserCredentials> adminList);

  List<UserVO> getAllAdmins();

  UserVO getAdminById(String id);

  boolean saveAdminDetails(UserDetailsVO userDetailsVO, String adminId);

  UserAndDetailsVO getAdminWithDetails(String userId);

}
