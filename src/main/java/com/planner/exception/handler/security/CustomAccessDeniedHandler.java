package com.planner.exception.handler.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planner.rest.helper.ResponseMessages;
import com.planner.rest.response.Response;
import java.io.IOException;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
      AccessDeniedException e) throws IOException, ServletException {

    if (httpServletResponse.containsHeader("token")) {
      httpServletResponse.setHeader("token", "XX-Unauthorized-XX");
    }
    if (httpServletResponse.containsHeader("Authorization")) {
      httpServletResponse.setHeader("Authorization", "XX-Unauthorized-XX");
    }
    httpServletResponse.setContentType("application/json");
    Response response = new Response();
    ObjectMapper mapper = new ObjectMapper();
    ResponseMessages errors = new ResponseMessages();
    response.setTimeStamp(Calendar.getInstance());
    errors.setErrorMessage("Unauthorized");
    errors.setMessageCode("999");
    response.setError(errors);
    httpServletResponse.setStatus(401);
    mapper.writeValue(httpServletResponse.getWriter(), response);

  }
}
