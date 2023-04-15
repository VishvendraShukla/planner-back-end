package com.planner.utils.configuration;

public interface Constants {


  final class Urls {

    public static final String SAVE = "/save";
    public static final String FETCH = "/fetch-all";
    public static final String FETCH_BY_ID = "/fetch/{id}";
    public static final String GET_TASK_CATEGORY = "/get-task-category";
    public static final String GET_USER = "/get-user";
    public static final String UPDATE = "/update";
    public static final String SAVE_DETAILS = "/save/details";
    public static final String GET_USER_DETAILS = "/get/user-details";
    public static final String SAVE_ADMIN_DETAILS_BY_ID = "/save/admin-details/{id}";
    public static final String FETCH_ADMIN_DETAILS_BY_ID = "/fetch/admin-details/{id}";
    public static final String CREATE_TASK_CATEGORY = "/create/category";
    public static final String FETCH_TASK_BY_TASK_ID = "/{taskId}/category/{taskCategoryId}";
    public static final String REMOVE_TASK_CATEGORY_BY_ID = "/{taskCategoryId}";
    public static final String REMOVE_TASK = "/{taskId}/category/{taskCategoryId}";
  }

  final class ControllerMapping {

    public static final String USER = "/user";
    public static final String ADMIN = "/admin";
    public static final String ACCESS = "/access";
    public static final String TASKS = "/tasks";
    public static final String TASK_CATEGORY = "/task-category";
  }

  final class AccessUrls {

    public static final String LOGIN = "/login";
    public static final String GENERATE = "/generate";
    public static final String GENERATE_TOKEN = "/generate/token";
    public static final String SIGN_UP = "/sign-up";
  }

  final class IgnoredApiUrls {
    public static String getAllIgnoreUrls = "/access/sign-up";
  }

  // for user start with 8xx
  // for category start with 6xx
  // for task start with 7xx
  // for general use 5xx

  final class ErrorMessagesCodes{
    // user
    public static final String USER_NOT_FOUND_ERROR_CODE = "8AA";
    public static final String USER_BLOCKED_ERROR_CODE = "8AB";
    public static final String UNAUTHORIZED_USER_ACCESS_ERROR_CODE = "8AC";
    public static final String USER_DETAILS_NOT_FOUND_ERROR_CODE = "8AD";
    public static final String INCORRECT_USER_DATA_ERROR_CODE = "8AE";

    // task category
    public static final String TASK_CATEGORY_NOT_FOUND_ERROR_CODE = "6AA";

    //task
    public static final String TASK_NOT_FOUND_ERROR_CODE = "7AA";

    // general
    public static final String BAD_CREDENTIALS_ERROR_CODE = "5AA";
    public static final String BASE_EXCEPTION_ERROR_CODE = "5AB";
  }

  final class ErrorMessages{

    // user
    public static final String USER_NOT_FOUND_ERROR_MESSAGE = "Request user not found, please try with correct data";
    public static final String USER_BLOCKED_ERROR_MESSAGE = "Requested user is blocked, please contact admin.";
    public static final String UNAUTHORIZED_USER_ACCESS_ERROR_MESSAGE = "Unauthorized Access";
    public static final String USER_DETAILS_NOT_FOUND_ERROR_MESSAGE = "No details Found";
    public static final String INCORRECT_USER_DATA_ERROR_MESSAGE = "Incorrect Data";

    // task category
    public static final String TASK_CATEGORY_NOT_FOUND_ERROR_MESSAGE = "Task category not found";

    //task
    public static final String TASK_NOT_FOUND_ERROR_MESSAGE = "Task not found";

    // general
    public static final String BAD_CREDENTIALS_ERROR_MESSAGE = "Unauthorized";
    public static final String BASE_EXCEPTION_ERROR_MESSAGE = "Some issue occurred, team is working on it";

  }
}
