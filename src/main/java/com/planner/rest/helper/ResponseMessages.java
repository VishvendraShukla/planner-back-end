package com.planner.rest.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"messageCode", "errorMessage"})
@JsonInclude(Include.NON_NULL)
public class ResponseMessages {

  @JsonProperty("messageCode")
  private String messageCode;
  @JsonProperty("errorMessage")
  private String errorMessage;

}
