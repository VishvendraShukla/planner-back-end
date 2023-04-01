package com.planner.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.planner.utils.IdVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupVO extends IdVO {

  @JsonProperty("user")
  private UserCredentials userCredentials;
  @JsonProperty("details")
  private UserDetailsVO userDetails;

  public String toString() {
    return "{ \"userCredentials\":" + "\""+ this.getUserCredentials() +"\""+",\"userDetails\":" + "\""+
        this.getUserDetails() + "\"}";
  }
}
