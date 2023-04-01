package com.planner.rest.access;

import static com.planner.utils.configuration.Constants.AccessUrls.GENERATE;
import static com.planner.utils.configuration.Constants.AccessUrls.GENERATE_TOKEN;
import static com.planner.utils.configuration.Constants.AccessUrls.LOGIN;
import static com.planner.utils.configuration.Constants.AccessUrls.SIGN_UP;
import static com.planner.utils.configuration.Constants.ControllerMapping.ACCESS;

import com.planner.entities.Users;
import com.planner.model.LoginCredentials;
import com.planner.model.SignupVO;
import com.planner.model.UserVO;
import com.planner.rest.helper.RestAPIResponse;
import com.planner.rest.response.Response;
import com.planner.service.UserService;
import com.planner.utils.access.LoggedUser;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ACCESS)
public class AccessRestController {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private UserService userService;

  @PutMapping(LOGIN)
  public ResponseEntity<Response> login(@RequestBody LoginCredentials loginCredentials) {
    UserVO userVO = userService.loginUpdate(loginCredentials);
    return RestAPIResponse.successResponse(userVO);
  }

  @PostMapping(GENERATE)
  public ResponseEntity<Response> generate(HttpServletRequest request) {
    Users users = LoggedUser.getUser();
    logger.info("simple token generated for {}, logId = {}", users.toString(),
        (String) request.getAttribute("logId"));
    return RestAPIResponse.successResponse("Verified");
  }

  @PostMapping(GENERATE_TOKEN)
  public ResponseEntity<Response> generated(@RequestBody LoginCredentials loginCredentials,
      HttpServletRequest request) {
    Users users = LoggedUser.getUser();
    logger.info("auth token generated for {}, logId = {}", users.toString(),
        (String) request.getAttribute("logId"));
    return RestAPIResponse.successResponse("Authenticated");
  }

  @PostMapping(SIGN_UP)
  public ResponseEntity<Response> signup(@RequestBody SignupVO signupVO,HttpServletRequest request) {
    logger.info(signupVO.toString() + " with logId={}", request.getAttribute("logId"));
//    userService.signUp(signupVO);
    return RestAPIResponse.successResponse("Successfully signed up!");
  }

}
