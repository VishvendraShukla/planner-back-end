package com.planner.rest.admin;

import static com.planner.utils.configuration.Constants.ControllerMapping.ADMIN;
import static com.planner.utils.configuration.Constants.Urls.FETCH;
import static com.planner.utils.configuration.Constants.Urls.FETCH_ADMIN_DETAILS_BY_ID;
import static com.planner.utils.configuration.Constants.Urls.FETCH_BY_ID;
import static com.planner.utils.configuration.Constants.Urls.SAVE;
import static com.planner.utils.configuration.Constants.Urls.SAVE_ADMIN_DETAILS_BY_ID;

import com.planner.exception.EntityMisMatchException;
import com.planner.model.UserAndDetailsVO;
import com.planner.model.UserCredentials;
import com.planner.model.UserDetailsVO;
import com.planner.model.UserVO;
import com.planner.rest.helper.RestAPIResponse;
import com.planner.rest.response.Response;
import com.planner.service.AdminService;
import com.planner.utils.status.UserType;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ADMIN)
public class AdminRestController {


  @Autowired
  private AdminService adminService;

  @GetMapping(value = FETCH)
  public ResponseEntity<Response> fetchAllAdmins() {
    List<UserVO> adminList = adminService.getAllAdmins();
    return RestAPIResponse.successResponse(adminList);
  }

  @GetMapping(value = FETCH_BY_ID)
  public ResponseEntity<Response> fetchUserById(@PathVariable String id) {
    UserVO adminById = adminService.getAdminById(id);
    return RestAPIResponse.successResponse(adminById);
  }

  @GetMapping(value = FETCH_ADMIN_DETAILS_BY_ID)
  public ResponseEntity<Response> getAdminDetails(@PathVariable("id") String id){
    UserAndDetailsVO userAndDetailsVO = adminService.getAdminWithDetails(id);
    return RestAPIResponse.successResponse(userAndDetailsVO);
  }

  @PostMapping(value = SAVE)
  public ResponseEntity<Response> persistAUser(@RequestBody UserCredentials user) {
    if (user.getUserType() == null || user.getUserType()
        .equalsIgnoreCase(UserType.MEMBER.getValue())) {
      throw new EntityMisMatchException("Incorrect Details");
    }
    UserVO persistedAdmin = adminService.createAdmin(user);
    return RestAPIResponse.successResponse(persistedAdmin);
  }

  @PostMapping(value = SAVE_ADMIN_DETAILS_BY_ID)
  public ResponseEntity<Response> saveAdminDetails(@PathVariable("id") String adminId, @RequestBody
      UserDetailsVO userDetailsVO) {
    if (adminService.saveAdminDetails(userDetailsVO, adminId)) {
      return RestAPIResponse.successResponse("Successfully saved user details");
    }
    return RestAPIResponse.errorResponse("Unable to save details", "999");
  }
}
