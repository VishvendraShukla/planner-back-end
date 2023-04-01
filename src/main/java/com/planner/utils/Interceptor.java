package com.planner.utils;

import com.planner.entities.Users;
import com.planner.repository.UserRepository;
import com.planner.security.model.JWTTokenHelper;
import com.planner.utils.access.LoggedUser;
import com.planner.utils.configuration.Constants.IgnoredApiUrls;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class Interceptor implements HandlerInterceptor {

  private final Logger logger = LoggerFactory.getLogger(Interceptor.class);

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JWTTokenHelper jwtTokenHelper;


  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    long requestStartTime = System.currentTimeMillis();
    String logId = UUID.randomUUID().toString();
    request.setAttribute("startTime", requestStartTime);
    request.setAttribute("logId", logId);
    if (urlToBeIgnored(request)) {
      logger.info(
          "Incoming Request from address={} for ignoredURL={} logId={}",
          request.getRemoteAddr(),
          request.getRequestURI(),
          logId
      );
      return true;
    }
    if (request.getHeader("Authorization") != null && request.getHeader("Authorization")
        .contains("Bearer")) {
      Optional<Users> user = userRepository.findUserByUsername(
          jwtTokenHelper.getUserNameFromJwtToken(parseJWT(request)));
      LoggedUser.init(user.get());
    } else {
      if (LoggedUser.getUsername() != null || !LoggedUser.getUsername().isEmpty()) {
        String username = LoggedUser.getUsername();
        Optional<Users> user = userRepository.findUserByUsername(username);
        LoggedUser.init(user.get());
      }
    }
    logger.info(
        "Incoming Request from address={} for URL={} owner={}, logId={}",
        request.getRemoteAddr(),
        request.getRequestURI(),
        LoggedUser.getUsername(),
        logId
    );
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    long requestEndTime = System.currentTimeMillis();
    long requestStartTime = (long) request.getAttribute("startTime");
    String logId = (String) request.getAttribute("logId");
    request.removeAttribute("startTime");
    int responseStatus = response.getStatus();
    String requestType = request.getMethod();
    if (urlToBeIgnored(request)) {
      logger.info("Service provided in {} ms with {} for HTTP {}, logId={}",
          requestEndTime - requestStartTime,
          responseStatus,
          requestType,
          logId
      );
    } else {
      logger.info("Service provided in {} ms for owner={} with {} for HTTP {}, logId={}",
          requestEndTime - requestStartTime,
          LoggedUser.getUser().getUsername(),
          responseStatus,
          requestType,
          logId
      );
    }
  }

  private String parseJWT(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    String token = "";
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      token = bearerToken.substring(7, bearerToken.length());
    } else {
      throw new BadCredentialsException("Bad Credentials");
    }
    return token;
  }

  private boolean urlToBeIgnored(HttpServletRequest request) {
    try {
      List<String> ignoredUrls = Arrays.asList(IgnoredApiUrls.getAllIgnoreUrls.split(","));
      return ignoredUrls.contains(request.getRequestURI());
    } catch (Exception e) {
    }
    return false;
  }
}
