package com.planner.exception.user;

import com.planner.exception.BaseException;

public class UserNotFoundException extends BaseException {

  public UserNotFoundException() {
  }

  public UserNotFoundException(String message) {
    super(message);
  }

  public UserNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public UserNotFoundException(Throwable cause) {
    super(cause);
  }

  public UserNotFoundException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
