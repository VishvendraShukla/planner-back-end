package com.planner.permission;

import com.planner.entities.Users;
import com.planner.exception.user.UnauthorizedUserAccessException;
import com.planner.exception.user.UserBlockedException;
import com.planner.exception.user.UserNotFoundException;
import com.planner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userPermission")
public class UserPermissionImpl {

  @Autowired
  private UserRepository userRepository;

  public void isPermitted(String id, Users users) {
    Users users1 = userRepository.findById(Long.valueOf(id))
        .orElseThrow(UserNotFoundException::new);
    isUserBlocked(users);
    if (!users.equals(users1)) {
      throw new UnauthorizedUserAccessException();
    }
  }

  public void isUserBlocked(Users users) {
    if (users.isBlocked()) {
      throw new UserBlockedException();
    }
  }

}
