package com.planner.rest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.planner.rest.helper.ResponseMessages;
import java.util.Calendar;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"status", "userBlocked", "payload", "message", "error", "passwordExpired",
    "timeStamp"})
public class Response {

  @JsonProperty("payload")
  private Object payload;
  @JsonProperty("message")
  private String message;
  @JsonProperty("status")
  private boolean status;
  @JsonProperty("passwordExpired")
  private boolean isPasswordExpired;
  @JsonProperty("userBlocked")
  private boolean isUserBlocked;
  @JsonProperty("timeStamp")
  private Calendar timeStamp;
  @JsonProperty("error")
  private ResponseMessages error;

}
