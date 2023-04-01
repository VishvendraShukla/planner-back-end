package com.planner.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCredentials {

  private String username;
  private String password;
  private String userType;

  public String toString() {
    return "{\"username\":" + "\"" + this.getUsername() + "\"" + ",\"userType\":" + "\"" + this.getUserType() + "\"" + "}";
  }
}
