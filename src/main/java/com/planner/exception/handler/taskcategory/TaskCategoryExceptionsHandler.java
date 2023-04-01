package com.planner.exception.handler.taskcategory;


import static com.planner.utils.configuration.Constants.ErrorMessages.TASK_CATEGORY_NOT_FOUND_ERROR_MESSAGE;
import static com.planner.utils.configuration.Constants.ErrorMessagesCodes.TASK_CATEGORY_NOT_FOUND_ERROR_CODE;

import com.planner.exception.taskcategory.TaskCategoryNotFoundException;
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
@Order(2)
public class TaskCategoryExceptionsHandler {

  private final Logger logger = LoggerFactory.getLogger(TaskCategoryExceptionsHandler.class);

  @ExceptionHandler(TaskCategoryNotFoundException.class)
  public ResponseEntity<Response> handleTaskCategoryNotFound(
      TaskCategoryNotFoundException taskCategoryNotFoundException) {
    logger.error(taskCategoryNotFoundException.getMessage());
    Response response = new Response();
    ResponseMessages errorMessages = new ResponseMessages();
    errorMessages.setMessageCode(TASK_CATEGORY_NOT_FOUND_ERROR_CODE);
    errorMessages.setErrorMessage(TASK_CATEGORY_NOT_FOUND_ERROR_MESSAGE);
    response.setError(errorMessages);
    return RestAPIResponse.errorResponse(response, HttpStatus.NOT_FOUND);
  }

}
