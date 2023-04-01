package com.planner.exception.handler;

import static com.planner.utils.configuration.Constants.ErrorMessages.BAD_CREDENTIALS_ERROR_MESSAGE;
import static com.planner.utils.configuration.Constants.ErrorMessages.BASE_EXCEPTION_ERROR_MESSAGE;
import static com.planner.utils.configuration.Constants.ErrorMessagesCodes.BAD_CREDENTIALS_ERROR_CODE;
import static com.planner.utils.configuration.Constants.ErrorMessagesCodes.BASE_EXCEPTION_ERROR_CODE;

import com.planner.exception.BaseException;
import com.planner.rest.helper.ResponseMessages;
import com.planner.rest.helper.RestAPIResponse;
import com.planner.rest.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(4)
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(BaseExceptionHandler.class);

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<Response> handleBadCredentialsExceptions(
      BadCredentialsException badCredentialsException) {
    logger.error(badCredentialsException.getMessage());
    Response response = new Response();
    ResponseMessages errorMessages = new ResponseMessages();
    errorMessages.setMessageCode(BAD_CREDENTIALS_ERROR_CODE);
    errorMessages.setErrorMessage(BAD_CREDENTIALS_ERROR_MESSAGE);
    response.setError(errorMessages);
    return RestAPIResponse.errorResponse(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<Response> handleUserBlocked(BaseException baseException) {
    logger.error(baseException.getMessage());
    Response response = new Response();
    ResponseMessages errorMessages = new ResponseMessages();
    errorMessages.setMessageCode(BASE_EXCEPTION_ERROR_CODE);
    errorMessages.setErrorMessage(BASE_EXCEPTION_ERROR_MESSAGE);
    response.setError(errorMessages);
    return RestAPIResponse.errorResponse(response, HttpStatus.OK);
  }

}
