package com.planner.exception.user;

import com.planner.exception.BaseException;

public class UserDataNotFoundException extends BaseException {

  public UserDataNotFoundException() {
  }

  public UserDataNotFoundException(String message) {
    super(message);
  }

  public UserDataNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public UserDataNotFoundException(Throwable cause) {
    super(cause);
  }

  public UserDataNotFoundException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
