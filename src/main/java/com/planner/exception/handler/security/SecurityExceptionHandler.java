package com.planner.exception.handler.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planner.rest.helper.ResponseMessages;
import com.planner.rest.response.Response;
import java.io.IOException;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

@Service("securityExceptionHandler")
public class SecurityExceptionHandler implements AuthenticationEntryPoint {

  private final static Logger logger = LoggerFactory.getLogger(SecurityExceptionHandler.class);

  @Override
  public void commence(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, AuthenticationException e)
      throws IOException {
    String remoteAddress = httpServletRequest.getRemoteAddr();
    String remoteHost = httpServletRequest.getRemoteHost();
    String requestUrl = httpServletRequest.getRequestURI();
    logger.error(
        "Request having address={} for URL={} of remote host={} is rejected because it was unauthorized.",
        remoteAddress, requestUrl, remoteHost);
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
