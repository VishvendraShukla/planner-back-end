package com.planner.rest.user;

import static com.planner.utils.configuration.Constants.ControllerMapping.USER;
import static com.planner.utils.configuration.Constants.Urls.FETCH;
import static com.planner.utils.configuration.Constants.Urls.GET_USER;
import static com.planner.utils.configuration.Constants.Urls.GET_USER_DETAILS;
import static com.planner.utils.configuration.Constants.Urls.SAVE;
import static com.planner.utils.configuration.Constants.Urls.SAVE_DETAILS;

import com.planner.entities.Users;
import com.planner.model.UserAndDetailsVO;
import com.planner.model.UserCredentials;
import com.planner.model.UserDetailsVO;
import com.planner.model.UserVO;
import com.planner.permission.UserPermissionImpl;
import com.planner.rest.helper.RestAPIResponse;
import com.planner.rest.response.Response;
import com.planner.service.UserService;
import com.planner.utils.access.LoggedUser;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(USER)
public class UserRestController {

  @Autowired
  private UserService userService;

  @Resource(name = "userPermission")
  private UserPermissionImpl userPermission;

  @GetMapping(value = FETCH)
  public ResponseEntity<Response> fetchAllUsers() {
    List<UserVO> userVOList = userService.getAllUsers();
    return RestAPIResponse.successResponse(userVOList);
  }

  @GetMapping(value = GET_USER)
  public ResponseEntity<Response> getUserById() {
    Users user = LoggedUser.getUser();
    UserVO userVO = userService.getUserById(String.valueOf(user.getId()));
    return RestAPIResponse.successResponse(userVO);
  }

  @GetMapping(value = GET_USER_DETAILS)
  public ResponseEntity<Response> getUserWithDetails() {
    UserAndDetailsVO userWithDetails = userService.getUserWithDetails(
        String.valueOf(LoggedUser.getUser().getId()));
    return RestAPIResponse.successResponse(userWithDetails);
  }

  @PostMapping(value = SAVE)
  public ResponseEntity<Response> persistAUser(@RequestBody UserCredentials user) {
    UserVO userVO = userService.save(user);
    return RestAPIResponse.successResponse(userVO);
  }

  @PostMapping(value = SAVE_DETAILS)
  public ResponseEntity<Response> saveUserDetails(@RequestBody UserDetailsVO userDetailsVO) {
    userService.saveUserDetails(userDetailsVO, String.valueOf(LoggedUser.getUser().getId()));
    return RestAPIResponse.successResponse("Successfully saved user details");
  }


}
