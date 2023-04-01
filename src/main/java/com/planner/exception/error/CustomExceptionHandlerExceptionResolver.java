package com.planner.exception.error;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class CustomExceptionHandlerExceptionResolver implements
    HandlerExceptionResolver, Ordered {

  private final static Logger logger = LoggerFactory.getLogger(CustomExceptionHandlerExceptionResolver.class);

  @Override
  public int getOrder() {
    return Integer.MIN_VALUE;
  }

  @Override
  public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) {
    try {
      if (response.containsHeader("token")) {
        response.setHeader("token", "XX-Unauthorized-XX");
      }
      if (response.containsHeader("Authorization")) {
        response.setHeader("Authorization", "XX-Unauthorized-XX");
      }
    } catch (Exception e) {
      logger.error(e.getMessage(),e);
    }
    return new ModelAndView("error.jsp");
  }

}
