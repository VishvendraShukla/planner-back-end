package com.planner.exception.task;

import com.planner.exception.BaseException;

public class TaskNotFoundException extends BaseException {

  public TaskNotFoundException() {
    super();
  }

  public TaskNotFoundException(String message) {
    super(message);
  }

  public TaskNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public TaskNotFoundException(Throwable cause) {
    super(cause);
  }

  public TaskNotFoundException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
