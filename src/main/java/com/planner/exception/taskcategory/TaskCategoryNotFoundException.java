package com.planner.exception.taskcategory;

import com.planner.exception.BaseException;

public class TaskCategoryNotFoundException extends BaseException {

  public TaskCategoryNotFoundException() {
    super();
  }

  public TaskCategoryNotFoundException(String message) {
    super(message);
  }

  public TaskCategoryNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public TaskCategoryNotFoundException(Throwable cause) {
    super(cause);
  }

  public TaskCategoryNotFoundException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
