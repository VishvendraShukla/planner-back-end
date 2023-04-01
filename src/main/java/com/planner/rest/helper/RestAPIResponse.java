package com.planner.rest.helper;

import com.planner.rest.response.Response;
import java.util.Calendar;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RestAPIResponse {

  public static ResponseEntity<Response> successResponse(Object payload) {
    Response response = new Response();
    response.setStatus(true);
    response.setPayload(payload);
    response.setPasswordExpired(false);
    response.setUserBlocked(false);
    response.setTimeStamp(Calendar.getInstance());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  public static ResponseEntity<Response> successResponse(Object payload, String message) {
    Response response = new Response();
    response.setStatus(true);
    response.setPayload(payload);
    response.setMessage(message);
    response.setPasswordExpired(false);
    response.setUserBlocked(false);
    response.setTimeStamp(Calendar.getInstance());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  public static ResponseEntity<Response> errorResponse() {
    Response response = new Response();
    response.setStatus(false);
    response.setPasswordExpired(false);
    response.setUserBlocked(false);
    response.setTimeStamp(Calendar.getInstance());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  public static ResponseEntity<Response> errorResponse(String message, String messageCode) {
    Response response = new Response();
    response.setStatus(false);
    ResponseMessages responseMessages = new ResponseMessages();
    responseMessages.setErrorMessage(message);
    responseMessages.setMessageCode(messageCode);
    response.setError(responseMessages);
    response.setPasswordExpired(false);
    response.setUserBlocked(false);
    response.setTimeStamp(Calendar.getInstance());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  public static ResponseEntity<Response> errorResponse(Response response, HttpStatus status) {
    response.setStatus(false);
    return new ResponseEntity<>(response, status);
  }


}
