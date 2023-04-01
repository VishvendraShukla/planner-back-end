package com.planner.repository;

import com.planner.entities.Users;
import com.planner.utils.status.UserType;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<Users> {

  Optional<Users> findUserByUsername(String username);

  List<Users> findUserByUserType(UserType userType);
}