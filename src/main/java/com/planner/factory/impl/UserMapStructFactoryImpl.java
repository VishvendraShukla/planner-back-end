package com.planner.factory.impl;

import com.planner.entities.RoleEntity;
import com.planner.entities.UserDetails;
import com.planner.entities.Users;
import com.planner.exception.user.UserDataNotFoundException;
import com.planner.exception.user.UserNotFoundException;
import com.planner.factory.UserMapStructFactory;
import com.planner.model.SignupVO;
import com.planner.model.UserAndDetailsVO;
import com.planner.model.UserCredentials;
import com.planner.model.UserDetailsVO;
import com.planner.model.UserVO;
import com.planner.repository.RoleRepository;
import com.planner.utils.PlannerDateFormatter;
import com.planner.utils.hash.PasswordHasher;
import com.planner.utils.status.UserType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMapStructFactoryImpl implements UserMapStructFactory {

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PasswordHasher passwordHasher;

  @Override
  public Users getUserForPersisting(UserCredentials userCredentials) {
    Users newUser = new Users();
    newUser.setId(null);
    newUser.setUsername(userCredentials.getUsername());
    newUser.setLastLogin(Calendar.getInstance());
    newUser.setCreationDate(Calendar.getInstance());
    newUser.setModifiedDate(Calendar.getInstance());
    if (userCredentials.getUserType() == null || userCredentials.getUserType().equals("null")) {
      Optional<RoleEntity> userRole = roleRepository.findByName("ROLE_USER");
      RoleEntity roleEntity = userRole.orElseThrow(NoSuchFieldError::new);
      newUser.addRole(roleEntity);
      newUser.setUserType(UserType.MEMBER);
    } else {
      Optional<RoleEntity> userRole = roleRepository.findByName("ROLE_ADMIN");
      RoleEntity roleEntity = userRole.orElseThrow(NoSuchFieldError::new);
      newUser.addRole(roleEntity);
      newUser.setUserType(UserType.ADMINISTRATOR);
    }
    String salt = passwordHasher.generateSalt();
    newUser.setSalt(salt);
    newUser.setPassword(passwordHasher.hash(userCredentials.getPassword(),salt));
    newUser.setBlocked(false);
    return newUser;
  }

  @Override
  public UserVO createVoForRestResponse(Users user) {
    if (user == null) {
      throw new UserNotFoundException("Not Found");
    }
    UserVO userVO = new UserVO();
    userVO.setId(user.getId());
    userVO.setUsername(user.getUsername());
    userVO.setLastModified(user.getLastLogin());
    userVO.setUserType(user.getUserType().getValue());
    return userVO;
  }

  @Override
  public List<UserVO> createVoForRestResponse(List<Users> usersList) {
    if (usersList == null) {
      throw new UserNotFoundException("Not Found");
    }
    List<UserVO> userVOList = new ArrayList<>();
    usersList.stream().forEach(user -> {
      userVOList.add(createVoForRestResponse(user));
    });
    return userVOList;
  }

  @Override
  public UserDetails getUserDetailsForPersisting(UserDetailsVO userDetailsVO) {
    Calendar currentTime = Calendar.getInstance();
    UserDetails userDetails = new UserDetails();
    userDetails.setUsername(userDetailsVO.getUserName());
    userDetails.setFirstName(userDetailsVO.getFirstName());
    userDetails.setMiddleName(userDetailsVO.getMiddleName());
    userDetails.setLastName(userDetailsVO.getLastName());
    userDetails.setEmail(userDetailsVO.getEmail());
    if (userDetailsVO.getDob() != null) {
      Calendar dateOfBirth = PlannerDateFormatter.convertToCalendar(userDetailsVO.getDob(),
          PlannerDateFormatter.DD_MM_YYYY);
      userDetails.setDateOfBirth(dateOfBirth);
    }
    userDetails.setAddress(userDetailsVO.getAddress());
    userDetails.setCity(userDetailsVO.getCity());
    userDetails.setCountry(userDetailsVO.getCountry());
    userDetails.setMobile(userDetailsVO.getMobileNumber());
    userDetails.setCreationDate(currentTime);
    userDetails.setModifiedDate(currentTime);
    return userDetails;
  }

  @Override
  public UserAndDetailsVO createUserWithDetailsVO(Users user) {
    if (user.getUserDetails() == null) {
      throw new UserDataNotFoundException("Details not found");
    }
    UserAndDetailsVO userAndDetailsVO = new UserAndDetailsVO();
    userAndDetailsVO.setUser(createVoForRestResponse(user));
    userAndDetailsVO.setUserDetails(convertDetailsToVO(user.getUserDetails()));
    return userAndDetailsVO;
  }

  @Override
  public Users mapToEntity(SignupVO signupVO) {
    Users user = getUserForPersisting(signupVO.getUserCredentials());
    user.setUserDetails(getUserDetailsForPersisting(signupVO.getUserDetails()));
    return user;
  }

  private UserDetailsVO convertDetailsToVO(UserDetails userDetails) {
    UserDetailsVO userDetailsVO = new UserDetailsVO();
    userDetailsVO.setId(userDetails.getId());
    userDetailsVO.setUserName(userDetails.getUsername());
    userDetailsVO.setFirstName(userDetails.getFirstName());
    userDetailsVO.setMiddleName(userDetails.getMiddleName());
    userDetailsVO.setLastName(userDetails.getLastName());
    userDetailsVO.setEmail(userDetails.getEmail());
    userDetailsVO.setDob(PlannerDateFormatter.convertToYYYYMMDD(userDetails.getDateOfBirth()));
    userDetailsVO.setAddress(userDetails.getAddress());
    userDetailsVO.setCity(userDetails.getCity());
    userDetailsVO.setCountry(userDetails.getCountry());
    userDetailsVO.setMobileNumber(userDetails.getMobile());
    return userDetailsVO;

  }

}
