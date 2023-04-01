package com.planner.exception.handler.user;

import static com.planner.utils.configuration.Constants.ErrorMessages.INCORRECT_USER_DATA_ERROR_MESSAGE;
import static com.planner.utils.configuration.Constants.ErrorMessages.UNAUTHORIZED_USER_ACCESS_ERROR_MESSAGE;
import static com.planner.utils.configuration.Constants.ErrorMessages.USER_BLOCKED_ERROR_MESSAGE;
import static com.planner.utils.configuration.Constants.ErrorMessages.USER_DETAILS_NOT_FOUND_ERROR_MESSAGE;
import static com.planner.utils.configuration.Constants.ErrorMessages.USER_NOT_FOUND_ERROR_MESSAGE;
import static com.planner.utils.configuration.Constants.ErrorMessagesCodes.INCORRECT_USER_DATA_ERROR_CODE;
import static com.planner.utils.configuration.Constants.ErrorMessagesCodes.UNAUTHORIZED_USER_ACCESS_ERROR_CODE;
import static com.planner.utils.configuration.Constants.ErrorMessagesCodes.USER_BLOCKED_ERROR_CODE;
import static com.planner.utils.configuration.Constants.ErrorMessagesCodes.USER_DETAILS_NOT_FOUND_ERROR_CODE;
import static com.planner.utils.configuration.Constants.ErrorMessagesCodes.USER_NOT_FOUND_ERROR_CODE;

import com.planner.exception.user.IncorrectDataException;
import com.planner.exception.user.UnauthorizedUserAccessException;
import com.planner.exception.user.UserBlockedException;
import com.planner.exception.user.UserDataNotFoundException;
import com.planner.exception.user.UserNotFoundException;
import com.planner.rest.helper.ResponseMessages;
import com.planner.rest.helper.RestAPIResponse;
import com.planner.rest.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(1)
public class UserExceptionsHandler extends ResponseEntityExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(UserExceptionsHandler.class);

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<Response> handleUserNotFound(UserNotFoundException userNotFoundException) {
    logger.error(userNotFoundException.getMessage());
    Response response = new Response();
    ResponseMessages errorMessages = new ResponseMessages();
    errorMessages.setMessageCode(USER_NOT_FOUND_ERROR_CODE);
    errorMessages.setErrorMessage(USER_NOT_FOUND_ERROR_MESSAGE);
    response.setError(errorMessages);
    return RestAPIResponse.errorResponse(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UserBlockedException.class)
  public ResponseEntity<Response> handleUserBlocked(UserBlockedException userBlockedException) {
    logger.error(userBlockedException.getMessage());
    Response response = new Response();
    ResponseMessages errorMessages = new ResponseMessages();
    errorMessages.setMessageCode(USER_BLOCKED_ERROR_CODE);
    errorMessages.setErrorMessage(USER_BLOCKED_ERROR_MESSAGE);
    response.setError(errorMessages);
    return RestAPIResponse.errorResponse(response, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(UnauthorizedUserAccessException.class)
  public ResponseEntity<Response> handleUnauthorizedUserAccess(
      UnauthorizedUserAccessException userAccessException) {
    logger.error(userAccessException.getMessage());
    Response response = new Response();
    ResponseMessages errorMessages = new ResponseMessages();
    errorMessages.setMessageCode(UNAUTHORIZED_USER_ACCESS_ERROR_CODE);
    errorMessages.setErrorMessage(UNAUTHORIZED_USER_ACCESS_ERROR_MESSAGE);
    response.setError(errorMessages);
    return RestAPIResponse.errorResponse(response, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(UserDataNotFoundException.class)
  public ResponseEntity<Response> handleUserDetailsNotFound(
      UserDataNotFoundException userDataNotFoundException) {
    logger.error(userDataNotFoundException.getMessage());
    Response response = new Response();
    ResponseMessages errorMessages = new ResponseMessages();
    errorMessages.setMessageCode(USER_DETAILS_NOT_FOUND_ERROR_CODE);
    errorMessages.setErrorMessage(USER_DETAILS_NOT_FOUND_ERROR_MESSAGE);
    response.setError(errorMessages);
    return RestAPIResponse.errorResponse(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IncorrectDataException.class)
  public ResponseEntity<Response> handleIncorrectData(
      IncorrectDataException incorrectDataException) {
    logger.error(incorrectDataException.getMessage());
    Response response = new Response();
    ResponseMessages errorMessages = new ResponseMessages();
    errorMessages.setMessageCode(INCORRECT_USER_DATA_ERROR_CODE);
    errorMessages.setErrorMessage(INCORRECT_USER_DATA_ERROR_MESSAGE);
    response.setError(errorMessages);
    return RestAPIResponse.errorResponse(response, HttpStatus.BAD_REQUEST);
  }


}
