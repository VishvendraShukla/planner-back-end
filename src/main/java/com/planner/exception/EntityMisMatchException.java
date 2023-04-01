package com.planner.exception;

public class EntityMisMatchException extends BaseException {

  public EntityMisMatchException() {
  }

  public EntityMisMatchException(String message) {
    super(message);
  }

  public EntityMisMatchException(String message, Throwable cause) {
    super(message, cause);
  }

  public EntityMisMatchException(Throwable cause) {
    super(cause);
  }

  public EntityMisMatchException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
