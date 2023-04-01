package com.planner.rest.error;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ErrorController {

  private final static Logger logger = LoggerFactory.getLogger(
      ErrorController.class);

  @GetMapping("error")
  ModelAndView resolveError(HttpServletRequest request, HttpServletResponse response) {
    try {
      if (response.containsHeader("token")) {
        response.setHeader("token", "XX-Unauthorized-XX");
      }
      if (response.containsHeader("Authorization")) {
        response.setHeader("Authorization", "XX-Unauthorized-XX");
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return new ModelAndView("error.jsp");
  }

  @PostMapping("error")
  ModelAndView resolveErrorForPost(HttpServletRequest request, HttpServletResponse response) {
    try {
      if (response.containsHeader("token")) {
        response.setHeader("token", "XX-Unauthorized-XX");
      }
      if (response.containsHeader("Authorization")) {
        response.setHeader("Authorization", "XX-Unauthorized-XX");
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return new ModelAndView("error.jsp");
  }
}
