package com.planner.exception.handler.task;

import static com.planner.utils.configuration.Constants.ErrorMessages.TASK_NOT_FOUND_ERROR_MESSAGE;
import static com.planner.utils.configuration.Constants.ErrorMessagesCodes.TASK_NOT_FOUND_ERROR_CODE;

import com.planner.exception.task.TaskNotFoundException;
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

@ControllerAdvice
@Order(3)
public class TaskExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(TaskExceptionHandler.class);

  @ExceptionHandler(TaskNotFoundException.class)
  public ResponseEntity<Response> handleTaskNotFound(
      TaskNotFoundException taskNotFoundException) {
    logger.error(taskNotFoundException.getMessage());
    Response response = new Response();
    ResponseMessages errorMessages = new ResponseMessages();
    errorMessages.setMessageCode(TASK_NOT_FOUND_ERROR_CODE);
    errorMessages.setErrorMessage(TASK_NOT_FOUND_ERROR_MESSAGE);
    response.setError(errorMessages);
    return RestAPIResponse.errorResponse(response, HttpStatus.NOT_FOUND);
  }

}
