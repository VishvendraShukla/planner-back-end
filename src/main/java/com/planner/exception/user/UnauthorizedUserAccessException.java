package com.planner.exception.user;

import com.planner.exception.BaseException;

public class UnauthorizedUserAccessException extends BaseException {

  public UnauthorizedUserAccessException() {
  }

  public UnauthorizedUserAccessException(String message) {
    super(message);
  }

  public UnauthorizedUserAccessException(String message, Throwable cause) {
    super(message, cause);
  }

  public UnauthorizedUserAccessException(Throwable cause) {
    super(cause);
  }

  public UnauthorizedUserAccessException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
